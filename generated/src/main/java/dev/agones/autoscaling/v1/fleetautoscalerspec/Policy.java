package dev.agones.autoscaling.v1.fleetautoscalerspec;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"buffer","counter","list","type","webhook"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Policy implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("buffer")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.autoscaling.v1.fleetautoscalerspec.policy.Buffer buffer;

    public dev.agones.autoscaling.v1.fleetautoscalerspec.policy.Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(dev.agones.autoscaling.v1.fleetautoscalerspec.policy.Buffer buffer) {
        this.buffer = buffer;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("counter")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.autoscaling.v1.fleetautoscalerspec.policy.Counter counter;

    public dev.agones.autoscaling.v1.fleetautoscalerspec.policy.Counter getCounter() {
        return counter;
    }

    public void setCounter(dev.agones.autoscaling.v1.fleetautoscalerspec.policy.Counter counter) {
        this.counter = counter;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("list")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.autoscaling.v1.fleetautoscalerspec.policy.List list;

    public dev.agones.autoscaling.v1.fleetautoscalerspec.policy.List getList() {
        return list;
    }

    public void setList(dev.agones.autoscaling.v1.fleetautoscalerspec.policy.List list) {
        this.list = list;
    }

    public enum Type {

        @com.fasterxml.jackson.annotation.JsonProperty("Buffer")
        BUFFER("Buffer"), @com.fasterxml.jackson.annotation.JsonProperty("Webhook")
        WEBHOOK("Webhook"), @com.fasterxml.jackson.annotation.JsonProperty("Counter")
        COUNTER("Counter"), @com.fasterxml.jackson.annotation.JsonProperty("List")
        LIST("List");

        java.lang.String value;

        Type(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    @com.fasterxml.jackson.annotation.JsonProperty("type")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("webhook")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.autoscaling.v1.fleetautoscalerspec.policy.Webhook webhook;

    public dev.agones.autoscaling.v1.fleetautoscalerspec.policy.Webhook getWebhook() {
        return webhook;
    }

    public void setWebhook(dev.agones.autoscaling.v1.fleetautoscalerspec.policy.Webhook webhook) {
        this.webhook = webhook;
    }
}

