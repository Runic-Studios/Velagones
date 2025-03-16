package dev.agones.v1.gameserversetspec.template.spec.template.spec;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"name","source"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class ResourceClaims implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * Name uniquely identifies this resource claim inside the pod. This must be a DNS_LABEL.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("name")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Name uniquely identifies this resource claim inside the pod. This must be a DNS_LABEL.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Source describes where to find the ResourceClaim.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("source")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Source describes where to find the ResourceClaim.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserversetspec.template.spec.template.spec.resourceclaims.Source source;

    public dev.agones.v1.gameserversetspec.template.spec.template.spec.resourceclaims.Source getSource() {
        return source;
    }

    public void setSource(dev.agones.v1.gameserversetspec.template.spec.template.spec.resourceclaims.Source source) {
        this.source = source;
    }
}

