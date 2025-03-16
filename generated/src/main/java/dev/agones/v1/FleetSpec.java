package dev.agones.v1;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"allocationOverflow","priorities","replicas","scheduling","strategy","template"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class FleetSpec implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("allocationOverflow")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.v1.fleetspec.AllocationOverflow allocationOverflow;

    public dev.agones.v1.fleetspec.AllocationOverflow getAllocationOverflow() {
        return allocationOverflow;
    }

    public void setAllocationOverflow(dev.agones.v1.fleetspec.AllocationOverflow allocationOverflow) {
        this.allocationOverflow = allocationOverflow;
    }

    /**
     * Configuration of Counters and Lists scale down logic -- which gameservers in the Fleet are most important to keep around.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("priorities")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Configuration of Counters and Lists scale down logic -- which gameservers in the Fleet are most important to keep around.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.List<dev.agones.v1.fleetspec.Priorities> priorities;

    public java.util.List<dev.agones.v1.fleetspec.Priorities> getPriorities() {
        return priorities;
    }

    public void setPriorities(java.util.List<dev.agones.v1.fleetspec.Priorities> priorities) {
        this.priorities = priorities;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("replicas")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long replicas;

    public Long getReplicas() {
        return replicas;
    }

    public void setReplicas(Long replicas) {
        this.replicas = replicas;
    }

    public enum Scheduling {

        @com.fasterxml.jackson.annotation.JsonProperty("Packed")
        PACKED("Packed"), @com.fasterxml.jackson.annotation.JsonProperty("Distributed")
        DISTRIBUTED("Distributed");

        java.lang.String value;

        Scheduling(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    @com.fasterxml.jackson.annotation.JsonProperty("scheduling")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Scheduling scheduling;

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("strategy")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.Strategy strategy;

    public dev.agones.v1.fleetspec.Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(dev.agones.v1.fleetspec.Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * GameServer is the data structure for a GameServer resource.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("template")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("GameServer is the data structure for a GameServer resource.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.Template template;

    public dev.agones.v1.fleetspec.Template getTemplate() {
        return template;
    }

    public void setTemplate(dev.agones.v1.fleetspec.Template template) {
        this.template = template;
    }
}

