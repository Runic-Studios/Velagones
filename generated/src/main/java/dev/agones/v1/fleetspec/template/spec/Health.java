package dev.agones.v1.fleetspec.template.spec;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"disabled","failureThreshold","initialDelaySeconds","periodSeconds"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Health implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("disabled")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Boolean disabled;

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("failureThreshold")
    @io.fabric8.generator.annotation.Max(2.147483648E9)
    @io.fabric8.generator.annotation.Min(1.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long failureThreshold;

    public Long getFailureThreshold() {
        return failureThreshold;
    }

    public void setFailureThreshold(Long failureThreshold) {
        this.failureThreshold = failureThreshold;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("initialDelaySeconds")
    @io.fabric8.generator.annotation.Max(2.147483648E9)
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long initialDelaySeconds;

    public Long getInitialDelaySeconds() {
        return initialDelaySeconds;
    }

    public void setInitialDelaySeconds(Long initialDelaySeconds) {
        this.initialDelaySeconds = initialDelaySeconds;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("periodSeconds")
    @io.fabric8.generator.annotation.Max(2.147483648E9)
    @io.fabric8.generator.annotation.Min(0.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long periodSeconds;

    public Long getPeriodSeconds() {
        return periodSeconds;
    }

    public void setPeriodSeconds(Long periodSeconds) {
        this.periodSeconds = periodSeconds;
    }
}

