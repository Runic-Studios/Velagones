package dev.agones.autoscaling.v1;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"ableToScale","currentReplicas","desiredReplicas","lastScaleTime","scalingLimited"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class FleetAutoscalerStatus implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("ableToScale")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Boolean ableToScale;

    public Boolean getAbleToScale() {
        return ableToScale;
    }

    public void setAbleToScale(Boolean ableToScale) {
        this.ableToScale = ableToScale;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("currentReplicas")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long currentReplicas;

    public Long getCurrentReplicas() {
        return currentReplicas;
    }

    public void setCurrentReplicas(Long currentReplicas) {
        this.currentReplicas = currentReplicas;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("desiredReplicas")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long desiredReplicas;

    public Long getDesiredReplicas() {
        return desiredReplicas;
    }

    public void setDesiredReplicas(Long desiredReplicas) {
        this.desiredReplicas = desiredReplicas;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("lastScaleTime")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SET)
    @io.fabric8.generator.annotation.Nullable()
    private java.time.ZonedDateTime lastScaleTime;

    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssVV")
    public java.time.ZonedDateTime getLastScaleTime() {
        return lastScaleTime;
    }

    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[XXX][VV]")
    public void setLastScaleTime(java.time.ZonedDateTime lastScaleTime) {
        this.lastScaleTime = lastScaleTime;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("scalingLimited")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Boolean scalingLimited;

    public Boolean getScalingLimited() {
        return scalingLimited;
    }

    public void setScalingLimited(Boolean scalingLimited) {
        this.scalingLimited = scalingLimited;
    }
}

