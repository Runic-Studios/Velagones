package dev.agones.v1;

@io.fabric8.kubernetes.model.annotation.Version(value = "v1" , storage = true , served = true)
@io.fabric8.kubernetes.model.annotation.Group("agones.dev")
@io.fabric8.kubernetes.model.annotation.Singular("fleet")
@io.fabric8.kubernetes.model.annotation.Plural("fleets")
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Fleet extends io.fabric8.kubernetes.client.CustomResource<dev.agones.v1.FleetSpec, dev.agones.v1.FleetStatus> implements io.fabric8.kubernetes.api.model.Namespaced {
}

