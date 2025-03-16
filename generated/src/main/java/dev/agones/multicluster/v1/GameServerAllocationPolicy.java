package dev.agones.multicluster.v1;

@io.fabric8.kubernetes.model.annotation.Version(value = "v1" , storage = true , served = true)
@io.fabric8.kubernetes.model.annotation.Group("multicluster.agones.dev")
@io.fabric8.kubernetes.model.annotation.Plural("gameserverallocationpolicies")
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class GameServerAllocationPolicy extends io.fabric8.kubernetes.client.CustomResource<dev.agones.multicluster.v1.GameServerAllocationPolicySpec, java.lang.Void> implements io.fabric8.kubernetes.api.model.Namespaced {
}

