package dev.agones.v1.fleetspec;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"rollingUpdate","type"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Strategy implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("rollingUpdate")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private dev.agones.v1.fleetspec.strategy.RollingUpdate rollingUpdate;

    public dev.agones.v1.fleetspec.strategy.RollingUpdate getRollingUpdate() {
        return rollingUpdate;
    }

    public void setRollingUpdate(dev.agones.v1.fleetspec.strategy.RollingUpdate rollingUpdate) {
        this.rollingUpdate = rollingUpdate;
    }

    public enum Type {

        @com.fasterxml.jackson.annotation.JsonProperty("Recreate")
        RECREATE("Recreate"), @com.fasterxml.jackson.annotation.JsonProperty("RollingUpdate")
        ROLLINGUPDATE("RollingUpdate");

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
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

