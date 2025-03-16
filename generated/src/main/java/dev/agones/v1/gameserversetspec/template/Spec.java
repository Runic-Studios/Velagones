package dev.agones.v1.gameserversetspec.template;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"container","counters","eviction","health","immutableReplicas","lists","players","ports","scheduling","sdkServer","template"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Spec implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * if there is more than one container, specify which one is the game server
     */
    @com.fasterxml.jackson.annotation.JsonProperty("container")
    @io.fabric8.generator.annotation.Pattern("^[a-z0-9]([-a-z0-9]*[a-z0-9])?$")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("if there is more than one container, specify which one is the game server")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String container;

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("counters")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.Map<java.lang.String, dev.agones.v1.gameserversetspec.template.spec.Counters> counters;

    public java.util.Map<java.lang.String, dev.agones.v1.gameserversetspec.template.spec.Counters> getCounters() {
        return counters;
    }

    public void setCounters(java.util.Map<java.lang.String, dev.agones.v1.gameserversetspec.template.spec.Counters> counters) {
        this.counters = counters;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("eviction")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserversetspec.template.spec.Eviction eviction;

    public dev.agones.v1.gameserversetspec.template.spec.Eviction getEviction() {
        return eviction;
    }

    public void setEviction(dev.agones.v1.gameserversetspec.template.spec.Eviction eviction) {
        this.eviction = eviction;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("health")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserversetspec.template.spec.Health health;

    public dev.agones.v1.gameserversetspec.template.spec.Health getHealth() {
        return health;
    }

    public void setHealth(dev.agones.v1.gameserversetspec.template.spec.Health health) {
        this.health = health;
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
    private java.util.Map<java.lang.String, dev.agones.v1.gameserversetspec.template.spec.Lists> lists;

    public java.util.Map<java.lang.String, dev.agones.v1.gameserversetspec.template.spec.Lists> getLists() {
        return lists;
    }

    public void setLists(java.util.Map<java.lang.String, dev.agones.v1.gameserversetspec.template.spec.Lists> lists) {
        this.lists = lists;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("players")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.v1.gameserversetspec.template.spec.Players players;

    public dev.agones.v1.gameserversetspec.template.spec.Players getPlayers() {
        return players;
    }

    public void setPlayers(dev.agones.v1.gameserversetspec.template.spec.Players players) {
        this.players = players;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("ports")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.util.List<dev.agones.v1.gameserversetspec.template.spec.Ports> ports;

    public java.util.List<dev.agones.v1.gameserversetspec.template.spec.Ports> getPorts() {
        return ports;
    }

    public void setPorts(java.util.List<dev.agones.v1.gameserversetspec.template.spec.Ports> ports) {
        this.ports = ports;
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

    @com.fasterxml.jackson.annotation.JsonProperty("sdkServer")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserversetspec.template.spec.SdkServer sdkServer;

    public dev.agones.v1.gameserversetspec.template.spec.SdkServer getSdkServer() {
        return sdkServer;
    }

    public void setSdkServer(dev.agones.v1.gameserversetspec.template.spec.SdkServer sdkServer) {
        this.sdkServer = sdkServer;
    }

    /**
     * PodTemplateSpec describes the data a pod should have when created from a template
     */
    @com.fasterxml.jackson.annotation.JsonProperty("template")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("PodTemplateSpec describes the data a pod should have when created from a template")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserversetspec.template.spec.Template template;

    public dev.agones.v1.gameserversetspec.template.spec.Template getTemplate() {
        return template;
    }

    public void setTemplate(dev.agones.v1.gameserversetspec.template.spec.Template template) {
        this.template = template;
    }
}

