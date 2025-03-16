package dev.agones.autoscaling.v1.fleetautoscalerspec.policy;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"caBundle","service","url"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Webhook implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("caBundle")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String caBundle;

    public String getCaBundle() {
        return caBundle;
    }

    public void setCaBundle(String caBundle) {
        this.caBundle = caBundle;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("service")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.autoscaling.v1.fleetautoscalerspec.policy.webhook.Service service;

    public dev.agones.autoscaling.v1.fleetautoscalerspec.policy.webhook.Service getService() {
        return service;
    }

    public void setService(dev.agones.autoscaling.v1.fleetautoscalerspec.policy.webhook.Service service) {
        this.service = service;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("url")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

