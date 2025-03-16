package dev.agones.v1;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"allocationOverflow","priorities","replicas","scheduling","template"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class GameServerSetSpec implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("allocationOverflow")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.v1.gameserversetspec.AllocationOverflow allocationOverflow;

    public dev.agones.v1.gameserversetspec.AllocationOverflow getAllocationOverflow() {
        return allocationOverflow;
    }

    public void setAllocationOverflow(dev.agones.v1.gameserversetspec.AllocationOverflow allocationOverflow) {
        this.allocationOverflow = allocationOverflow;
    }

    /**
     * Configuration of Counters and Lists scale down logic. Priorities in the gameserverset.yaml file must be identical to the structure of priorities in fleet.yaml.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("priorities")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Configuration of Counters and Lists scale down logic. Priorities in the gameserverset.yaml file must be identical to the structure of priorities in fleet.yaml.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.List<dev.agones.v1.gameserversetspec.Priorities> priorities;

    public java.util.List<dev.agones.v1.gameserversetspec.Priorities> getPriorities() {
        return priorities;
    }

    public void setPriorities(java.util.List<dev.agones.v1.gameserversetspec.Priorities> priorities) {
        this.priorities = priorities;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("replicas")
    @io.fabric8.generator.annotation.Required()
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

    /**
     * GameServer is the data structure for a GameServer resource.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("template")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("GameServer is the data structure for a GameServer resource.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserversetspec.Template template;

    public dev.agones.v1.gameserversetspec.Template getTemplate() {
        return template;
    }

    public void setTemplate(dev.agones.v1.gameserversetspec.Template template) {
        this.template = template;
    }
}

