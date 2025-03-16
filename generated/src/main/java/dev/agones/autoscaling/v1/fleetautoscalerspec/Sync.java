package dev.agones.autoscaling.v1.fleetautoscalerspec;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"fixedInterval","type"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Sync implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("fixedInterval")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.autoscaling.v1.fleetautoscalerspec.sync.FixedInterval fixedInterval;

    public dev.agones.autoscaling.v1.fleetautoscalerspec.sync.FixedInterval getFixedInterval() {
        return fixedInterval;
    }

    public void setFixedInterval(dev.agones.autoscaling.v1.fleetautoscalerspec.sync.FixedInterval fixedInterval) {
        this.fixedInterval = fixedInterval;
    }

    public enum Type {

        @com.fasterxml.jackson.annotation.JsonProperty("FixedInterval")
        FIXEDINTERVAL("FixedInterval");

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
}

