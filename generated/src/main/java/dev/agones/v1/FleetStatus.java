package dev.agones.v1;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"allocatedReplicas","counters","lists","players","readyReplicas","replicas","reservedReplicas"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class FleetStatus implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("allocatedReplicas")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long allocatedReplicas;

    public Long getAllocatedReplicas() {
        return allocatedReplicas;
    }

    public void setAllocatedReplicas(Long allocatedReplicas) {
        this.allocatedReplicas = allocatedReplicas;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("counters")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.Map<java.lang.String, dev.agones.v1.fleetstatus.Counters> counters;

    public java.util.Map<java.lang.String, dev.agones.v1.fleetstatus.Counters> getCounters() {
        return counters;
    }

    public void setCounters(java.util.Map<java.lang.String, dev.agones.v1.fleetstatus.Counters> counters) {
        this.counters = counters;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("lists")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.Map<java.lang.String, dev.agones.v1.fleetstatus.Lists> lists;

    public java.util.Map<java.lang.String, dev.agones.v1.fleetstatus.Lists> getLists() {
        return lists;
    }

    public void setLists(java.util.Map<java.lang.String, dev.agones.v1.fleetstatus.Lists> lists) {
        this.lists = lists;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("players")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.v1.fleetstatus.Players players;

    public dev.agones.v1.fleetstatus.Players getPlayers() {
        return players;
    }

    public void setPlayers(dev.agones.v1.fleetstatus.Players players) {
        this.players = players;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("readyReplicas")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long readyReplicas;

    public Long getReadyReplicas() {
        return readyReplicas;
    }

    public void setReadyReplicas(Long readyReplicas) {
        this.readyReplicas = readyReplicas;
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

    @com.fasterxml.jackson.annotation.JsonProperty("reservedReplicas")
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long reservedReplicas;

    public Long getReservedReplicas() {
        return reservedReplicas;
    }

    public void setReservedReplicas(Long reservedReplicas) {
        this.reservedReplicas = reservedReplicas;
    }
}

