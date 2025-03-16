package dev.agones.v1.gameserverstatus;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"safe"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Eviction implements io.fabric8.kubernetes.api.model.KubernetesResource {

    public enum Safe {

        @com.fasterxml.jackson.annotation.JsonProperty("Always")
        ALWAYS("Always"), @com.fasterxml.jackson.annotation.JsonProperty("OnUpgrade")
        ONUPGRADE("OnUpgrade"), @com.fasterxml.jackson.annotation.JsonProperty("Never")
        NEVER("Never");

        java.lang.String value;

        Safe(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    @com.fasterxml.jackson.annotation.JsonProperty("safe")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Safe safe;

    public Safe getSafe() {
        return safe;
    }

    public void setSafe(Safe safe) {
        this.safe = safe;
    }
}

