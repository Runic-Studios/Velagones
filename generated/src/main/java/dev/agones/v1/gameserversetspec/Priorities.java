package dev.agones.v1.gameserversetspec;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"key","order","type"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Priorities implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * The name of the Counter or List
     */
    @com.fasterxml.jackson.annotation.JsonProperty("key")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("The name of the Counter or List")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public enum Order {

        @com.fasterxml.jackson.annotation.JsonProperty("Ascending")
        ASCENDING("Ascending"), @com.fasterxml.jackson.annotation.JsonProperty("Descending")
        DESCENDING("Descending");

        java.lang.String value;

        Order(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    /**
     * Ascending or Descending sort order
     */
    @com.fasterxml.jackson.annotation.JsonProperty("order")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Ascending or Descending sort order")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public enum Type {

        @com.fasterxml.jackson.annotation.JsonProperty("Counter")
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

    /**
     * Whether a Counter or a List.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("type")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Whether a Counter or a List.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

