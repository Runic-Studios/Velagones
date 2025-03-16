package dev.agones.v1.gameserversetspec.template.spec.template.spec.volumes.ephemeral.volumeclaimtemplate.metadata;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"apiVersion","fieldsType","fieldsV1","manager","operation","subresource","time"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class ManagedFields implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * APIVersion defines the version of this resource that this field set applies to. The format is "group/version" just like the top-level APIVersion field. It is necessary to track the version of a field set because it cannot be automatically converted.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("apiVersion")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("APIVersion defines the version of this resource that this field set applies to. The format is \"group/version\" just like the top-level APIVersion field. It is necessary to track the version of a field set because it cannot be automatically converted.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String apiVersion;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * FieldsType is the discriminator for the different fields format and version. There is currently only one possible value: "FieldsV1"
     */
    @com.fasterxml.jackson.annotation.JsonProperty("fieldsType")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("FieldsType is the discriminator for the different fields format and version. There is currently only one possible value: \"FieldsV1\"")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String fieldsType;

    public String getFieldsType() {
        return fieldsType;
    }

    public void setFieldsType(String fieldsType) {
        this.fieldsType = fieldsType;
    }

    /**
     * FieldsV1 holds the first JSON version format as described in the "FieldsV1" type.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("fieldsV1")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("FieldsV1 holds the first JSON version format as described in the \"FieldsV1\" type.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.gameserversetspec.template.spec.template.spec.volumes.ephemeral.volumeclaimtemplate.metadata.managedfields.FieldsV1 fieldsV1;

    public dev.agones.v1.gameserversetspec.template.spec.template.spec.volumes.ephemeral.volumeclaimtemplate.metadata.managedfields.FieldsV1 getFieldsV1() {
        return fieldsV1;
    }

    public void setFieldsV1(dev.agones.v1.gameserversetspec.template.spec.template.spec.volumes.ephemeral.volumeclaimtemplate.metadata.managedfields.FieldsV1 fieldsV1) {
        this.fieldsV1 = fieldsV1;
    }

    /**
     * Manager is an identifier of the workflow managing these fields.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("manager")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Manager is an identifier of the workflow managing these fields.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String manager;

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * Operation is the type of operation which lead to this ManagedFieldsEntry being created. The only valid values for this field are 'Apply' and 'Update'.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("operation")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Operation is the type of operation which lead to this ManagedFieldsEntry being created. The only valid values for this field are 'Apply' and 'Update'.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Subresource is the name of the subresource used to update that object, or empty string if the object was updated through the main resource. The value of this field is used to distinguish between managers, even if they share the same name. For example, a status update will be distinct from a regular update using the same manager name. Note that the APIVersion field is not related to the Subresource field and it always corresponds to the version of the main resource.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("subresource")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Subresource is the name of the subresource used to update that object, or empty string if the object was updated through the main resource. The value of this field is used to distinguish between managers, even if they share the same name. For example, a status update will be distinct from a regular update using the same manager name. Note that the APIVersion field is not related to the Subresource field and it always corresponds to the version of the main resource.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String subresource;

    public String getSubresource() {
        return subresource;
    }

    public void setSubresource(String subresource) {
        this.subresource = subresource;
    }

    /**
     * Time is the timestamp of when the ManagedFields entry was added. The timestamp will also be updated if a field is added, the manager changes any of the owned fields value or removes a field. The timestamp does not update when a field is removed from the entry because another manager took it over.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("time")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Time is the timestamp of when the ManagedFields entry was added. The timestamp will also be updated if a field is added, the manager changes any of the owned fields value or removes a field. The timestamp does not update when a field is removed from the entry because another manager took it over.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private java.time.ZonedDateTime time;

    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssVV")
    public java.time.ZonedDateTime getTime() {
        return time;
    }

    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[XXX][VV]")
    public void setTime(java.time.ZonedDateTime time) {
        this.time = time;
    }
}

