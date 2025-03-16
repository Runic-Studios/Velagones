# Velagones

This project is intended to be a bridge between the Agones project for Kubernetes, and the PaperMC Velocity project which is a proxy for Minecraft servers.

Velagones is a project created by Runic Studios for Runic Realms, but is configurable for many similar use cases.

## Background

Agones is a Kubernetes extension that creates CRDs for GameServers, and Fleets (of game servers). Agones is useful when creating games for many reasons, but some of the popular ones include:

- Dynamic port assignment: Agones assigns each game server a port between 7000-7999 on the worker node for it to run off of, then allows for discovery of this port through Agones APIs
- Autoscaling and availability: Agones allows for custom tuning of autoscaling based on the number of game servers in-use, or number of players online. We can also configure idle servers to wait until their are allocated for extra availability.
- Game server allocation: Using the Agones Allocator Service, we can request fresh game servers either through a matchmaker or through custom solutions. 

Velocity is a proxy which can sit in front of many Paper Minecraft game servers. When deploying Velocity in an Agones setup to manage our Minecraft game servers, we run into an issue where Velocity is not automatically aware of new game servers that Kubernetes/Agones spin up. This project intends to bridge this gap, among other things.

## Solution

Velagones is installed as a <b>Plugin for the Velocity Proxy</b> that periodically monitors the Kubernetes Cluster it is in installed within to find updates to Game Servers.

Velagones utilizes the Kubernetes API directly to search for objects that have the GameServer CRD type installed by Agones.
It will automatically tell Velocity to register/deregister game servers that it finds in the K8s cluster.
### Configuration

Velagones needs to be told which namespace to search for game servers in. This is done through an injected `application.properties` that you put in the `plugins/velagones` folder.

The only property (so far) in `application.properties` should be:
```
velagones.game-server-namespace=MY_NAMESPACE
```

Note that other native spring boot application properties can be injected through this file as well.

## Implementation Details

Velagones uses:
- Spring Boot for autowiring configuration, and as the runtime wrapper
- Fabric8's Kubernetes Java generator to create java POJOs for the CRDs created by Agones
- The Kubernets Java API to access the cluster

While running on top of Velocity.