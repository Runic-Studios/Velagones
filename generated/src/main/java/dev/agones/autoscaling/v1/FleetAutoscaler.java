package dev.agones.autoscaling.v1;

@io.fabric8.kubernetes.model.annotation.Version(value = "v1" , storage = true , served = true)
@io.fabric8.kubernetes.model.annotation.Group("autoscaling.agones.dev")
@io.fabric8.kubernetes.model.annotation.Singular("fleetautoscaler")
@io.fabric8.kubernetes.model.annotation.Plural("fleetautoscalers")
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class FleetAutoscaler extends io.fabric8.kubernetes.client.CustomResource<dev.agones.autoscaling.v1.FleetAutoscalerSpec, dev.agones.autoscaling.v1.FleetAutoscalerStatus> implements io.fabric8.kubernetes.api.model.Namespaced {
}

