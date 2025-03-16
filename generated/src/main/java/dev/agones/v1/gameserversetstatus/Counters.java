package dev.agones.v1.gameserversetstatus;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"allocatedCapacity","allocatedCount","capacity","count"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Counters implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("allocatedCapacity")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long allocatedCapacity;

    public Long getAllocatedCapacity() {
        return allocatedCapacity;
    }

    public void setAllocatedCapacity(Long allocatedCapacity) {
        this.allocatedCapacity = allocatedCapacity;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("allocatedCount")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long allocatedCount;

    public Long getAllocatedCount() {
        return allocatedCount;
    }

    public void setAllocatedCount(Long allocatedCount) {
        this.allocatedCount = allocatedCount;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("capacity")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long capacity;

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("count")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long count = 0L;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

