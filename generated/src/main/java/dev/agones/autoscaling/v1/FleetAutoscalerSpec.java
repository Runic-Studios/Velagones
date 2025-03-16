package dev.agones.autoscaling.v1;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"fleetName","policy","sync"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class FleetAutoscalerSpec implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("fleetName")
    @io.fabric8.generator.annotation.Required()
    @io.fabric8.generator.annotation.Pattern("^[a-z0-9]([-\\.a-z0-9]*[a-z0-9])?$")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String fleetName;

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("policy")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.autoscaling.v1.fleetautoscalerspec.Policy policy;

    public dev.agones.autoscaling.v1.fleetautoscalerspec.Policy getPolicy() {
        return policy;
    }

    public void setPolicy(dev.agones.autoscaling.v1.fleetautoscalerspec.Policy policy) {
        this.policy = policy;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("sync")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.autoscaling.v1.fleetautoscalerspec.Sync sync;

    public dev.agones.autoscaling.v1.fleetautoscalerspec.Sync getSync() {
        return sync;
    }

    public void setSync(dev.agones.autoscaling.v1.fleetautoscalerspec.Sync sync) {
        this.sync = sync;
    }
}

