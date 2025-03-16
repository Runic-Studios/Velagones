package dev.agones.v1;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"address","addresses","counters","eviction","immutableReplicas","lists","nodeName","players","ports","reservedUntil","state"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class GameServerStatus implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("address")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("addresses")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.List<dev.agones.v1.gameserverstatus.Addresses> addresses;

    public java.util.List<dev.agones.v1.gameserverstatus.Addresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(java.util.List<dev.agones.v1.gameserverstatus.Addresses> addresses) {
        this.addresses = addresses;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("counters")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.Map<java.lang.String, dev.agones.v1.gameserverstatus.Counters> counters;

    public java.util.Map<java.lang.String, dev.agones.v1.gameserverstatus.Counters> getCounters() {
        return counters;
    }

    public void setCounters(java.util.Map<java.lang.String, dev.agones.v1.gameserverstatus.Counters> counters) {
        this.counters = counters;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("eviction")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserverstatus.Eviction eviction;

    public dev.agones.v1.gameserverstatus.Eviction getEviction() {
        return eviction;
    }

    public void setEviction(dev.agones.v1.gameserverstatus.Eviction eviction) {
        this.eviction = eviction;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("immutableReplicas")
    @io.fabric8.generator.annotation.Max(1.0)
    @io.fabric8.generator.annotation.Min(1.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long immutableReplicas = 1L;

    public Long getImmutableReplicas() {
        return immutableReplicas;
    }

    public void setImmutableReplicas(Long immutableReplicas) {
        this.immutableReplicas = immutableReplicas;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("lists")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.Map<java.lang.String, dev.agones.v1.gameserverstatus.Lists> lists;

    public java.util.Map<java.lang.String, dev.agones.v1.gameserverstatus.Lists> getLists() {
        return lists;
    }

    public void setLists(java.util.Map<java.lang.String, dev.agones.v1.gameserverstatus.Lists> lists) {
        this.lists = lists;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("nodeName")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String nodeName;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("players")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.v1.gameserverstatus.Players players;

    public dev.agones.v1.gameserverstatus.Players getPlayers() {
        return players;
    }

    public void setPlayers(dev.agones.v1.gameserverstatus.Players players) {
        this.players = players;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("ports")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.List<dev.agones.v1.gameserverstatus.Ports> ports;

    public java.util.List<dev.agones.v1.gameserverstatus.Ports> getPorts() {
        return ports;
    }

    public void setPorts(java.util.List<dev.agones.v1.gameserverstatus.Ports> ports) {
        this.ports = ports;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("reservedUntil")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.time.ZonedDateTime reservedUntil;

    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssVV")
    public java.time.ZonedDateTime getReservedUntil() {
        return reservedUntil;
    }

    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[XXX][VV]")
    public void setReservedUntil(java.time.ZonedDateTime reservedUntil) {
        this.reservedUntil = reservedUntil;
    }

    public enum State {

        @com.fasterxml.jackson.annotation.JsonProperty("PortAllocation")
        PORTALLOCATION("PortAllocation"),
        @com.fasterxml.jackson.annotation.JsonProperty("Creating")
        CREATING("Creating"),
        @com.fasterxml.jackson.annotation.JsonProperty("Starting")
        STARTING("Starting"),
        @com.fasterxml.jackson.annotation.JsonProperty("Scheduled")
        SCHEDULED("Scheduled"),
        @com.fasterxml.jackson.annotation.JsonProperty("RequestReady")
        REQUESTREADY("RequestReady"),
        @com.fasterxml.jackson.annotation.JsonProperty("Ready")
        READY("Ready"),
        @com.fasterxml.jackson.annotation.JsonProperty("Shutdown")
        SHUTDOWN("Shutdown"),
        @com.fasterxml.jackson.annotation.JsonProperty("Error")
        ERROR("Error"),
        @com.fasterxml.jackson.annotation.JsonProperty("Unhealthy")
        UNHEALTHY("Unhealthy"),
        @com.fasterxml.jackson.annotation.JsonProperty("Reserved")
        RESERVED("Reserved"),
        @com.fasterxml.jackson.annotation.JsonProperty("Allocated")
        ALLOCATED("Allocated");

        java.lang.String value;

        State(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    @com.fasterxml.jackson.annotation.JsonProperty("state")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

