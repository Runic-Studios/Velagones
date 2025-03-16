package dev.agones.v1;

@io.fabric8.kubernetes.model.annotation.Version(value = "v1" , storage = true , served = true)
@io.fabric8.kubernetes.model.annotation.Group("agones.dev")
@io.fabric8.kubernetes.model.annotation.Singular("gameserverset")
@io.fabric8.kubernetes.model.annotation.Plural("gameserversets")
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class GameServerSet extends io.fabric8.kubernetes.client.CustomResource<dev.agones.v1.GameServerSetSpec, dev.agones.v1.GameServerSetStatus> implements io.fabric8.kubernetes.api.model.Namespaced {
}

