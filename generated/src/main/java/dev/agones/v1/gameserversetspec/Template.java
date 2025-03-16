package dev.agones.v1.gameserversetspec;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"metadata","spec"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Template implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * ObjectMeta is metadata that all persisted resources must have, which includes all objects users must create.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("metadata")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("ObjectMeta is metadata that all persisted resources must have, which includes all objects users must create.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserversetspec.template.Metadata metadata;

    public dev.agones.v1.gameserversetspec.template.Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(dev.agones.v1.gameserversetspec.template.Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * GameServerSpec is the spec for a GameServer resource. More info: https://agones.dev/site/docs/reference/agones_crd_api_reference/#agones.dev/v1.GameServer
     */
    @com.fasterxml.jackson.annotation.JsonProperty("spec")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("GameServerSpec is the spec for a GameServer resource. More info: https://agones.dev/site/docs/reference/agones_crd_api_reference/#agones.dev/v1.GameServer")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserversetspec.template.Spec spec;

    public dev.agones.v1.gameserversetspec.template.Spec getSpec() {
        return spec;
    }

    public void setSpec(dev.agones.v1.gameserversetspec.template.Spec spec) {
        this.spec = spec;
    }
}

