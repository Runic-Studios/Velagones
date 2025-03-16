package dev.agones.v1.fleetspec.template.spec.template.spec.volumes;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"path","type"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class HostPath implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * path of the directory on the host. If the path is a symlink, it will follow the link to the real path. More info: https://kubernetes.io/docs/concepts/storage/volumes#hostpath
     */
    @com.fasterxml.jackson.annotation.JsonProperty("path")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("path of the directory on the host. If the path is a symlink, it will follow the link to the real path. More info: https://kubernetes.io/docs/concepts/storage/volumes#hostpath")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public enum Type {

        @com.fasterxml.jackson.annotation.JsonProperty("")
        _EMPTY(""),
        @com.fasterxml.jackson.annotation.JsonProperty("BlockDevice")
        BLOCKDEVICE("BlockDevice"),
        @com.fasterxml.jackson.annotation.JsonProperty("CharDevice")
        CHARDEVICE("CharDevice"),
        @com.fasterxml.jackson.annotation.JsonProperty("Directory")
        DIRECTORY("Directory"),
        @com.fasterxml.jackson.annotation.JsonProperty("DirectoryOrCreate")
        DIRECTORYORCREATE("DirectoryOrCreate"),
        @com.fasterxml.jackson.annotation.JsonProperty("File")
        FILE("File"),
        @com.fasterxml.jackson.annotation.JsonProperty("FileOrCreate")
        FILEORCREATE("FileOrCreate"),
        @com.fasterxml.jackson.annotation.JsonProperty("Socket")
        SOCKET("Socket");

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
     * type for HostPath Volume Defaults to "" More info: https://kubernetes.io/docs/concepts/storage/volumes#hostpath
     *
     * Possible enum values:
     *  - `""` For backwards compatible, leave it empty if unset
     *  - `"BlockDevice"` A block device must exist at the given path
     *  - `"CharDevice"` A character device must exist at the given path
     *  - `"Directory"` A directory must exist at the given path
     *  - `"DirectoryOrCreate"` If nothing exists at the given path, an empty directory will be created there as needed with file mode 0755, having the same group and ownership with Kubelet.
     *  - `"File"` A file must exist at the given path
     *  - `"FileOrCreate"` If nothing exists at the given path, an empty file will be created there as needed with file mode 0644, having the same group and ownership with Kubelet.
     *  - `"Socket"` A UNIX socket must exist at the given path
     */
    @com.fasterxml.jackson.annotation.JsonProperty("type")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("type for HostPath Volume Defaults to \"\" More info: https://kubernetes.io/docs/concepts/storage/volumes#hostpath\n\nPossible enum values:\n - `\"\"` For backwards compatible, leave it empty if unset\n - `\"BlockDevice\"` A block device must exist at the given path\n - `\"CharDevice\"` A character device must exist at the given path\n - `\"Directory\"` A directory must exist at the given path\n - `\"DirectoryOrCreate\"` If nothing exists at the given path, an empty directory will be created there as needed with file mode 0755, having the same group and ownership with Kubelet.\n - `\"File\"` A file must exist at the given path\n - `\"FileOrCreate\"` If nothing exists at the given path, an empty file will be created there as needed with file mode 0644, having the same group and ownership with Kubelet.\n - `\"Socket\"` A UNIX socket must exist at the given path")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

