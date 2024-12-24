# Velagones

This project is intended to be a bridge between the Agones project for Kubernetes, and the PaperMC Velocity project which is a proxy for Minecraft servers.

Velagones is a project created by Runic Studios for Runic Realms, but is configurable for many similar use cases.

## Background
Agones is a Kubernetes extension that creates CRDs for GameServers, and Fleets (of game servers). Agones is useful when creating games for many reasons, but some of the popular ones include:
- Dynamic port assignment: Agones assigns each game server a port between 7000-7999 on the worker node for it to run off of, then allows for discovery of this port through Agones APIs
- Autoscaling and availability: Agones allows for custom tuning of autoscaling based on the number of game servers in-use, or number of players online. We can also configure idle servers to wait until their are allocated for extra availability.
- Game server allocation: Using the Agones Allocator Service, we can request fresh game servers either through a matchmaker or through custom solutions.

Velocity is a proxy which can sit in front of many Paper Minecraft game servers. When deploying Velocity in an Agones setup to manage our Minecraft game servers,  we run into an issue where <b>Velocity is not automatically aware of new game servers that Kubernetes/Agones spin up</b>.
This project intends to bridge this gap, among other things.

## Solution

Velagones needs to be installed as a plugin for both the Velocity server and for each paper server.
- The <b>Velagones Discovery</b> gRPC server runs on the Velocity server
- The <b>Velagones Discovery</b> gRPC stub runs on each paper server

The Discovery RPC has two endpoints: `join` and `leave`. Paper servers will request to be exposed behind the proxy once they have finished starting up, and will remove themselves from the proxy (via the RPC) once they shut down.

Also, Velagones can be configured to mark each server as ALLOCATED in Agones (as opposed to READY) as soon as they join the proxy, but this behaviour may need to vary depending on your setup.

[COMING SOON] By default, when Paper servers shut down they notify the Velagones Discovery RPC to remove them from the Velocity Proxy, but this isn't guaranteed to work in the case of a server crash. Therefore, the Velagones Velocity Plugin periodically checks the configured Agones fleets to see that all of its clients are still alive, and removes any missing ones.

## Installation
### Agones + Kubernetes
The following components should be deployed in your Kubernetes cluster:
- A gameserver fleet with any name. Note that you can run multiple fleets, but Velagones needs to be made aware of all of them.
  Sample:
```yaml
apiVersion: "agones.dev/v1"  
kind: Fleet  
metadata:  
  name: mygame-fleet  
  namespace: mygame
spec:  
  replicas: 1  # Initial idle GameServers  
  template:  
    spec:  
      template:  
        metadata:  
          labels:  
            app: gameserver  
        spec:  
          containers:  
            - name: gameserver  
              image: MY_DOCKER_IMAGE:VERSION
              env:  # Custom envvars
                - name: ENV  
                  value: dev
```
- A single pod running the Velocity proxy. Sample:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: velocity
  labels:
    app: velocity
spec:
  containers:
    - name: velocity
      image: MY_VELOCITY_IMAGE:VERSION
      ports:
        - containerPort: 25565 # Default Minecraft client port
          name: minecraft
        - containerPort: 25577 # Default Velocity server port
          name: velocity
        - containerPort: 9357 # Default port for Velagones gRPC
          name: velagones-port
      # Optionally mount a volume for velocity config
      env:
        - name: VELOCITY_CONFIG_PATH
          value: "/path/to/velocity/config"
      volumeMounts:
        - name: velocity-config
          mountPath: "/path/to/velocity/config"
  volumes:
    - name: velocity-config
      configMap:
        name: velocity-config-map
```
- A Service that points to the Velocity proxy's Velagones gRPC server port (default is 9357).
    - This is used for the Velagones paper plugins to find the gRPC Discovery server.
    - Sample:
```yaml
apiVersion: v1
kind: Service
metadata:
  name: velagones-discovery # Will need this later!
spec:
  selector:
    app: velocity # Should match the label on the velocity pod
  ports:
    - protocol: TCP
      port: 9357 # Default port for Velagones paper plugins to target
      targetPort: 9357 # Default port for Velagones velocity plugins to host on
```
Names and labels of these resources will be necessary for you to configure both the Paper and Velocity Velagones plugins later.

### Velagones
Download the latest plugin jars from the Releases tab in GitHub. They each need to be installed as either a Paper or a Velocity plugin.
## Configuration (Required!)
Velagones needs to be configured so that the Paper and Velocity plugins are aware of how to communicate with each other in the cluster.

Make sure you modify <b>both</b> `config.yml` files for the Paper and Velocity plugins.

Paper `config.yml`:
```yml
discovery:  
  service:  
    # The name of the Kubernetes service that sits in front of the Velagones gRPC server on Velocity  
  name: velagones-discovery  
    # Namespace of the service  
  namespace: default  
  
agones:  
  # What is the local port to host agones health checks on?  
  local-port: 9357
  # Should this plugin shut down the paper server when there is no agones to connect to?  
  shutdown-when-missing: true
  # Should we allocate this instance in agones when we join the velocity proxy?
  allocate-on-join-velocity: true
```
COMING SOON