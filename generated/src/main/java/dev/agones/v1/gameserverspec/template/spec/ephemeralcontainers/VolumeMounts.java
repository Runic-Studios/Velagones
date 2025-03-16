package dev.agones.v1.gameserverspec.template.spec.ephemeralcontainers;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"mountPath","mountPropagation","name","readOnly","recursiveReadOnly","subPath","subPathExpr"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class VolumeMounts implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * Path within the container at which the volume should be mounted.  Must not contain ':'.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("mountPath")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Path within the container at which the volume should be mounted.  Must not contain ':'.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String mountPath;

    public String getMountPath() {
        return mountPath;
    }

    public void setMountPath(String mountPath) {
        this.mountPath = mountPath;
    }

    public enum MountPropagation {

        @com.fasterxml.jackson.annotation.JsonProperty("Bidirectional")
        BIDIRECTIONAL("Bidirectional"), @com.fasterxml.jackson.annotation.JsonProperty("HostToContainer")
        HOSTTOCONTAINER("HostToContainer"), @com.fasterxml.jackson.annotation.JsonProperty("None")
        NONE("None");

        java.lang.String value;

        MountPropagation(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    /**
     * mountPropagation determines how mounts are propagated from the host to container and the other way around. When not set, MountPropagationNone is used. This field is beta in 1.10. When RecursiveReadOnly is set to IfPossible or to Enabled, MountPropagation must be None or unspecified (which defaults to None).
     *
     * Possible enum values:
     *  - `"Bidirectional"` means that the volume in a container will receive new mounts from the host or other containers, and its own mounts will be propagated from the container to the host or other containers. Note that this mode is recursively applied to all mounts in the volume ("rshared" in Linux terminology).
     *  - `"HostToContainer"` means that the volume in a container will receive new mounts from the host or other containers, but filesystems mounted inside the container won't be propagated to the host or other containers. Note that this mode is recursively applied to all mounts in the volume ("rslave" in Linux terminology).
     *  - `"None"` means that the volume in a container will not receive new mounts from the host or other containers, and filesystems mounted inside the container won't be propagated to the host or other containers. Note that this mode corresponds to "private" in Linux terminology.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("mountPropagation")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("mountPropagation determines how mounts are propagated from the host to container and the other way around. When not set, MountPropagationNone is used. This field is beta in 1.10. When RecursiveReadOnly is set to IfPossible or to Enabled, MountPropagation must be None or unspecified (which defaults to None).\n\nPossible enum values:\n - `\"Bidirectional\"` means that the volume in a container will receive new mounts from the host or other containers, and its own mounts will be propagated from the container to the host or other containers. Note that this mode is recursively applied to all mounts in the volume (\"rshared\" in Linux terminology).\n - `\"HostToContainer\"` means that the volume in a container will receive new mounts from the host or other containers, but filesystems mounted inside the container won't be propagated to the host or other containers. Note that this mode is recursively applied to all mounts in the volume (\"rslave\" in Linux terminology).\n - `\"None\"` means that the volume in a container will not receive new mounts from the host or other containers, and filesystems mounted inside the container won't be propagated to the host or other containers. Note that this mode corresponds to \"private\" in Linux terminology.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private MountPropagation mountPropagation;

    public MountPropagation getMountPropagation() {
        return mountPropagation;
    }

    public void setMountPropagation(MountPropagation mountPropagation) {
        this.mountPropagation = mountPropagation;
    }

    /**
     * This must match the Name of a Volume.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("name")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("This must match the Name of a Volume.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Mounted read-only if true, read-write otherwise (false or unspecified). Defaults to false.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("readOnly")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Mounted read-only if true, read-write otherwise (false or unspecified). Defaults to false.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Boolean readOnly;

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * RecursiveReadOnly specifies whether read-only mounts should be handled recursively.
     *
     * If ReadOnly is false, this field has no meaning and must be unspecified.
     *
     * If ReadOnly is true, and this field is set to Disabled, the mount is not made recursively read-only.  If this field is set to IfPossible, the mount is made recursively read-only, if it is supported by the container runtime.  If this field is set to Enabled, the mount is made recursively read-only if it is supported by the container runtime, otherwise the pod will not be started and an error will be generated to indicate the reason.
     *
     * If this field is set to IfPossible or Enabled, MountPropagation must be set to None (or be unspecified, which defaults to None).
     *
     * If this field is not specified, it is treated as an equivalent of Disabled.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("recursiveReadOnly")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("RecursiveReadOnly specifies whether read-only mounts should be handled recursively.\n\nIf ReadOnly is false, this field has no meaning and must be unspecified.\n\nIf ReadOnly is true, and this field is set to Disabled, the mount is not made recursively read-only.  If this field is set to IfPossible, the mount is made recursively read-only, if it is supported by the container runtime.  If this field is set to Enabled, the mount is made recursively read-only if it is supported by the container runtime, otherwise the pod will not be started and an error will be generated to indicate the reason.\n\nIf this field is set to IfPossible or Enabled, MountPropagation must be set to None (or be unspecified, which defaults to None).\n\nIf this field is not specified, it is treated as an equivalent of Disabled.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String recursiveReadOnly;

    public String getRecursiveReadOnly() {
        return recursiveReadOnly;
    }

    public void setRecursiveReadOnly(String recursiveReadOnly) {
        this.recursiveReadOnly = recursiveReadOnly;
    }

    /**
     * Path within the volume from which the container's volume should be mounted. Defaults to "" (volume's root).
     */
    @com.fasterxml.jackson.annotation.JsonProperty("subPath")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Path within the volume from which the container's volume should be mounted. Defaults to \"\" (volume's root).")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String subPath;

    public String getSubPath() {
        return subPath;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    /**
     * Expanded path within the volume from which the container's volume should be mounted. Behaves similarly to SubPath but environment variable references $(VAR_NAME) are expanded using the container's environment. Defaults to "" (volume's root). SubPathExpr and SubPath are mutually exclusive.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("subPathExpr")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Expanded path within the volume from which the container's volume should be mounted. Behaves similarly to SubPath but environment variable references $(VAR_NAME) are expanded using the container's environment. Defaults to \"\" (volume's root). SubPathExpr and SubPath are mutually exclusive.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String subPathExpr;

    public String getSubPathExpr() {
        return subPathExpr;
    }

    public void setSubPathExpr(String subPathExpr) {
        this.subPathExpr = subPathExpr;
    }
}

