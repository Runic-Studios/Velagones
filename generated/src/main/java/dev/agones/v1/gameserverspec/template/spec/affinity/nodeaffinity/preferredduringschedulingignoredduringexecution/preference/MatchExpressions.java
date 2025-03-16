package dev.agones.v1.gameserverspec.template.spec.affinity.nodeaffinity.preferredduringschedulingignoredduringexecution.preference;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"key","operator","values"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class MatchExpressions implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * The label key that the selector applies to.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("key")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("The label key that the selector applies to.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public enum Operator {

        @com.fasterxml.jackson.annotation.JsonProperty("DoesNotExist")
        DOESNOTEXIST("DoesNotExist"),
        @com.fasterxml.jackson.annotation.JsonProperty("Exists")
        EXISTS("Exists"),
        @com.fasterxml.jackson.annotation.JsonProperty("Gt")
        GT("Gt"),
        @com.fasterxml.jackson.annotation.JsonProperty("In")
        IN("In"),
        @com.fasterxml.jackson.annotation.JsonProperty("Lt")
        LT("Lt"),
        @com.fasterxml.jackson.annotation.JsonProperty("NotIn")
        NOTIN("NotIn");

        java.lang.String value;

        Operator(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    /**
     * Represents a key's relationship to a set of values. Valid operators are In, NotIn, Exists, DoesNotExist. Gt, and Lt.
     *
     * Possible enum values:
     *  - `"DoesNotExist"`
     *  - `"Exists"`
     *  - `"Gt"`
     *  - `"In"`
     *  - `"Lt"`
     *  - `"NotIn"`
     */
    @com.fasterxml.jackson.annotation.JsonProperty("operator")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Represents a key's relationship to a set of values. Valid operators are In, NotIn, Exists, DoesNotExist. Gt, and Lt.\n\nPossible enum values:\n - `\"DoesNotExist\"`\n - `\"Exists\"`\n - `\"Gt\"`\n - `\"In\"`\n - `\"Lt\"`\n - `\"NotIn\"`")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Operator operator;

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * An array of string values. If the operator is In or NotIn, the values array must be non-empty. If the operator is Exists or DoesNotExist, the values array must be empty. If the operator is Gt or Lt, the values array must have a single element, which will be interpreted as an integer. This array is replaced during a strategic merge patch.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("values")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("An array of string values. If the operator is In or NotIn, the values array must be non-empty. If the operator is Exists or DoesNotExist, the values array must be empty. If the operator is Gt or Lt, the values array must have a single element, which will be interpreted as an integer. This array is replaced during a strategic merge patch.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private java.util.List<String> values;

    public java.util.List<String> getValues() {
        return values;
    }

    public void setValues(java.util.List<String> values) {
        this.values = values;
    }
}

