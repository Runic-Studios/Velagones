package dev.agones.autoscaling.v1.fleetautoscalerspec.policy;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"bufferSize","maxReplicas","minReplicas"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Buffer implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("bufferSize")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private io.fabric8.kubernetes.api.model.IntOrString bufferSize;

    public io.fabric8.kubernetes.api.model.IntOrString getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(io.fabric8.kubernetes.api.model.IntOrString bufferSize) {
        this.bufferSize = bufferSize;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("maxReplicas")
    @io.fabric8.generator.annotation.Required()
    @io.fabric8.generator.annotation.Min(1.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long maxReplicas;

    public Long getMaxReplicas() {
        return maxReplicas;
    }

    public void setMaxReplicas(Long maxReplicas) {
        this.maxReplicas = maxReplicas;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("minReplicas")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long minReplicas;

    public Long getMinReplicas() {
        return minReplicas;
    }

    public void setMinReplicas(Long minReplicas) {
        this.minReplicas = minReplicas;
    }
}

