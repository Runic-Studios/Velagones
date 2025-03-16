package dev.agones.autoscaling.v1.fleetautoscalerspec.policy;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"bufferSize","key","maxCapacity","minCapacity"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class List implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("bufferSize")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private io.fabric8.kubernetes.api.model.IntOrString bufferSize;

    public io.fabric8.kubernetes.api.model.IntOrString getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(io.fabric8.kubernetes.api.model.IntOrString bufferSize) {
        this.bufferSize = bufferSize;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("key")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("maxCapacity")
    @io.fabric8.generator.annotation.Required()
    @io.fabric8.generator.annotation.Min(1.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long maxCapacity;

    public Long getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("minCapacity")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long minCapacity;

    public Long getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(Long minCapacity) {
        this.minCapacity = minCapacity;
    }
}

