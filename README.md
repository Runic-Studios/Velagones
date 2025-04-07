

# Velagones

This project is intended to be a bridge between the Agones project for Kubernetes, and the PaperMC Velocity project which is a proxy for Minecraft servers.

Velagones is a project created by Runic Studios for Runic Realms, but is configurable for many similar use cases.

## Background

Agones is a Kubernetes extension that creates CRDs for GameServers, and Fleets (of game servers). 
It has many features, but the only ones relevant to us are:
- Kubernetes-managed lifecycle of our GameServers
- Dynamic port assignment: Agones assigns each game server a port between 7000-7999 on the worker node for it to run off of, then allows for discovery of this port through Agones APIs
- Autoscaling and availability: We can custom tune auto-scaling based on the number of game servers in-use, or number of players online.

Velocity is a proxy which can sit in front of many PaperMC game servers. When deploying Velocity in an Agones setup, <b>Velocity is not automatically aware of new game servers that Kubernetes/Agones spin up</b>, and would have no way to route players to them. This project intends to bridge this gap, among other things.

<b>Note</b>: Velagones is intended to work for Minecraft proxy setups where players are not required to be on any one server in order to play their game, and all servers are treated as equal replicas. This is great for games where the reason for using Agones is to maintain equivalent replicas, but Velagones may not be a good match for those aiming to have match-making where players are guided to a specific server when they connect (such as with mini-games).

## Features

Velagones does a few things:
- Makes every backend Paper server Agones-aware by enabling health checks, and marking it as ready in the Agones SDK.
- Constantly monitors the K8s namespace it is in for GameServers that Agones spins up. It sends these servers a "Discovery Signal," and then adds them to the Velocity proxy so players can connect to them.
- Monitors the player counts network-wide and attempts to auto-scale the fleet of GameServers up or down.
- Routes connecting players to an active backend server with the lowest player count.
    - This relies on our assumption that all backend servers are equal replicas and it does not matter which one a player connects to.
    - A future setting may allow "Packed" connection handling instead.

## Implementation
Velagones needs to run as both a <b>plugin on the Velocity proxy</b>, and as a <b>plugin on each backend PaperMC server</b>.

<b>Velagones on Velocity</b> is responsible for
- Routing player connections
- Computing auto-scaling logic
- Maintaining an Agones-aware registry of all connected backend servers, and messaging them over gRPC

<b>Velagones on Paper</b> will be in charge of
- Starting a gRPC server for Velagones on Velocity to connect to
- Updating the GameServer's status in Agones SDK
  - This is necessary for Agones to be able to effectively manage the lifecycle of our GameServers in Kubernetes
  - Notice that while Agones offers [many GameServer state options](https://agones.dev/site/docs/reference/gameserver/#gameserver-state-diagram), we will only be using three:
      - SCHEDULED: GameServer pod has started, but Paper hasn't finished starting
      - READY: Velagones has marked the GameServer as ready to accept connections now that PaperMC has started
      - SHUTDOWN: Velagones on Paper has received a signal from Velagones on Velocity that it should shut itself down (as a result of autoscaling)
  - In particular, we are never using the ALLOCATED state.

Here is a diagram representing the communication we are describing:

![velagones](https://github.com/user-attachments/assets/7c13e66f-c07b-47e9-9d2e-a86280fe99da)

### Discovery
- When a new GameServer instance is started by Agones (which would originally be caused by Velagones autoscaling), Velagones on Paper marks it as READY in Agones.
- Velagones on Velocity notices this state change in the cluster, and sends a "Discovery Message" to Velagones on that Paper server using gRPC.
- Once discovery has been approved, Velagones on Velocity adds the new server to the Velocity proxy and its internal registry.
### Autoscaling
- Velagones on Velocity evaluates the need to scale up or down each time a player joins/leaves the network.
  - Details on the configuration of autoscaling are below.
- In the even of a scale up, Velagones on Velocity informs Agones via webhook of an increased need of replicas.
    - This will eventually trigger the discovery process.
### Deactivation
- In the even of a scale down, Velagones on Velocity informs the paper server that it is <b>being deactivated</b> via gRPC.
    - A <b>deactivated server</b> is one that cannot have new player connections, but can maintain old ones for a certain period until it is shutdown.
    - Deactivated servers are not considered in the autoscaling equation.
- Upon receiving a deactivation notice, Velagones on Paper will wait until all players have left, then mark itself as SHUTDOWN in Agones, and stop the Paper process

## Configuration

### Velagones on Velocity
`plugins/Velagones/application.conf`:
```conf
agones {
    gameServerNamespace = "default"
    autoscaleHostPort = 7070
}
scaling {
    serverCapacity = 20
    capacityFactor = 1.5
    minServers = 1
    up {
        minPlayersBefore = 10
        minSecondsBefore = 120
    }
    down {
        minSecondsBefore = 300
        hysteresis = 1.2
    }
}
```
- `agones.gameServerNamespace`: Which namespace to scan for GameServers in the K8s cluster.
    - Future plans to add individual fleet scanning.
- `agones.autoscaleHostPort`: Which port to host the auto-scaler webhook. Important for later configuration of Agones.
- `scaling.serverCapacity`: Maximum number of players each server can hold.
- `scaling.capacityFactor`: Goal for how many players we should be able to hold among all active servers.
    - If server capacity is 20, capacity factor is 1.5, and we have 30 players connected, we would aim to support 45, and thus want to have 3 servers open.
- `scaling.minServers`: Minimum number of active replicas to have at once (no including deactivated replicas).
- `scaling.up.minPlayersBefore`: Minimum number of players we must have across the network before triggering an scale up.
- `scaling.up.minSecondsBefore`: Minimum duration between scale up attempts.
- `scaling.down.minSecondsBefore`: Minimum duration between scale down attempts.
- `scaling.down.hysteresis`: Amount of "buffer capacity" we should need to keep before scaling down.
    - If server capacity is 30, capacity factor is 1.5, hysteresis is 1.2, and we have players 19 connected but 2 servers open, we <i>cannot</i> scale down until our player count decreases below `ceil(30 / 1.5 / 1.2) = 17`
    - This is to prevent constant scaling if we are on the boundary. Larger hysteresis values (up until 1.5) require larger dips in traffic before we scale down, and smaller values are more eager to scale down.

### Velagones on Paper
`plugins/Velagones/application.conf`:
```conf
service {
    hostPort = 50051
}
```
- `service.hostPort`: Which port to host our gRPC server on for communication with Velagones on Velocity. Important for later configuration of Agones.

### Agones/Kubernetes Namespace
- You should have Agones installed in a separate namespace from your fleets and Velocity proxy.
- The Velocity proxy is expected to be installed in a separate deployment and service.
- At least one fleet should be installed in the namespace with the Velocity proxy.

Sample fleet:
```yaml
apiVersion: agones.dev/v1
kind: Fleet
metadata:
  name: my-fleet
spec:
  replicas: 1
  template:
    spec:
      ports:
        - name: game           # MUST be named game
          containerPort: 25565
          protocol: TCP        # Important to specify, Agones defaults to UDP
        - name: grpc           # MUST be named grpc
          containerPort: 50051 # Should match configuration in Velagones on Paper
          protocol: TCP        # Important to specify, Agones defaults to UDP
      template:
        spec:
          containers:
            - name: paper-gameserver
              image: my-image:latest
```
Sample velocity deployment:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: velocity
spec:
  replicas: 1
  selector:
    matchLabels:
      app: velocity
  template:
    metadata:
      labels:
        app: velocity
    spec:
      serviceAccountName: velocity-sa
      containers:
        - name: velocity
          image: MY_IMAGE:latest
          ports:
            - name: minecraft
              containerPort: 25565
            - name: autoscale
              containerPort: 7070  # Should match configuration in Velagones on Velocity
```
Sample velocity service:
```yaml
apiVersion: v1
kind: Service
metadata:
  name: velocity-service
spec:
  selector:
    app: velocity
  ports:
    - protocol: TCP
      port: 25565
      targetPort: 25565
    - protocol: HTTP
      port: 7070
      targetPort: 7070  # Should match configuration in Velagones on Velocity
  type: ClusterIP
```
Fleet autoscaler (REQUIRED!)
```yaml
apiVersion: "autoscaling.agones.dev/v1"
kind: FleetAutoscaler
metadata:
  name: fleet-autoscaler
spec:
  fleetName: my-fleet  # Should match your fleet name
  policy:
    type: Webhook
    webhook:
      service:
        name: velocity-service
        path: /autoscale
        port: 7070  # Should match port in velocity service
```
