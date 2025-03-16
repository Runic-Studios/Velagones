package dev.agones.v1.fleetspec.template.spec.template.spec.initcontainers.securitycontext;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"localhostProfile","type"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class AppArmorProfile implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * localhostProfile indicates a profile loaded on the node that should be used. The profile must be preconfigured on the node to work. Must match the loaded name of the profile. Must be set if and only if type is "Localhost".
     */
    @com.fasterxml.jackson.annotation.JsonProperty("localhostProfile")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("localhostProfile indicates a profile loaded on the node that should be used. The profile must be preconfigured on the node to work. Must match the loaded name of the profile. Must be set if and only if type is \"Localhost\".")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String localhostProfile;

    public String getLocalhostProfile() {
        return localhostProfile;
    }

    public void setLocalhostProfile(String localhostProfile) {
        this.localhostProfile = localhostProfile;
    }

    public enum Type {

        @com.fasterxml.jackson.annotation.JsonProperty("Localhost")
        LOCALHOST("Localhost"), @com.fasterxml.jackson.annotation.JsonProperty("RuntimeDefault")
        RUNTIMEDEFAULT("RuntimeDefault"), @com.fasterxml.jackson.annotation.JsonProperty("Unconfined")
        UNCONFINED("Unconfined");

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
     * type indicates which kind of AppArmor profile will be applied. Valid options are:
     *   Localhost - a profile pre-loaded on the node.
     *   RuntimeDefault - the container runtime's default profile.
     *   Unconfined - no AppArmor enforcement.
     *
     * Possible enum values:
     *  - `"Localhost"` indicates that a profile pre-loaded on the node should be used.
     *  - `"RuntimeDefault"` indicates that the container runtime's default AppArmor profile should be used.
     *  - `"Unconfined"` indicates that no AppArmor profile should be enforced.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("type")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("type indicates which kind of AppArmor profile will be applied. Valid options are:\n  Localhost - a profile pre-loaded on the node.\n  RuntimeDefault - the container runtime's default profile.\n  Unconfined - no AppArmor enforcement.\n\nPossible enum values:\n - `\"Localhost\"` indicates that a profile pre-loaded on the node should be used.\n - `\"RuntimeDefault\"` indicates that the container runtime's default AppArmor profile should be used.\n - `\"Unconfined\"` indicates that no AppArmor profile should be enforced.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

