package dev.agones.v1.gameserversetspec.template.spec;

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

    /**
     * - Never: The game server should run to completion. Agones sets Pod annotation `cluster-autoscaler.kubernetes.io/safe-to-evict: "false"` and label `agones.dev/safe-to-evict: "false"`, which matches a restrictive PodDisruptionBudget.
     * - OnUpgrade: On SIGTERM, the game server will exit within `terminationGracePeriodSeconds` or be terminated; Agones sets Pod annotation `cluster-autoscaler.kubernetes.io/safe-to-evict: "false"`, which blocks evictions by Cluster Autoscaler. Evictions from node upgrades proceed normally.
     * - Always: On SIGTERM, the game server will exit within `terminationGracePeriodSeconds` or be terminated, typically within 10m; Agones sets Pod annotation `cluster-autoscaler.kubernetes.io/safe-to-evict: "true"`, which allows evictions by Cluster Autoscaler.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("safe")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("- Never: The game server should run to completion. Agones sets Pod annotation `cluster-autoscaler.kubernetes.io/safe-to-evict: \"false\"` and label `agones.dev/safe-to-evict: \"false\"`, which matches a restrictive PodDisruptionBudget.\n- OnUpgrade: On SIGTERM, the game server will exit within `terminationGracePeriodSeconds` or be terminated; Agones sets Pod annotation `cluster-autoscaler.kubernetes.io/safe-to-evict: \"false\"`, which blocks evictions by Cluster Autoscaler. Evictions from node upgrades proceed normally.\n- Always: On SIGTERM, the game server will exit within `terminationGracePeriodSeconds` or be terminated, typically within 10m; Agones sets Pod annotation `cluster-autoscaler.kubernetes.io/safe-to-evict: \"true\"`, which allows evictions by Cluster Autoscaler.\n")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Safe safe;

    public Safe getSafe() {
        return safe;
    }

    public void setSafe(Safe safe) {
        this.safe = safe;
    }
}

