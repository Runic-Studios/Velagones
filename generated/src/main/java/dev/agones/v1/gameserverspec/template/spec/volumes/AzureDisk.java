package dev.agones.v1.gameserverspec.template.spec.volumes;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"cachingMode","diskName","diskURI","fsType","kind","readOnly"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class AzureDisk implements io.fabric8.kubernetes.api.model.KubernetesResource {

    public enum CachingMode {

        @com.fasterxml.jackson.annotation.JsonProperty("None")
        NONE("None"), @com.fasterxml.jackson.annotation.JsonProperty("ReadOnly")
        READONLY("ReadOnly"), @com.fasterxml.jackson.annotation.JsonProperty("ReadWrite")
        READWRITE("ReadWrite");

        java.lang.String value;

        CachingMode(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    /**
     * cachingMode is the Host Caching mode: None, Read Only, Read Write.
     *
     * Possible enum values:
     *  - `"None"`
     *  - `"ReadOnly"`
     *  - `"ReadWrite"`
     */
    @com.fasterxml.jackson.annotation.JsonProperty("cachingMode")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("cachingMode is the Host Caching mode: None, Read Only, Read Write.\n\nPossible enum values:\n - `\"None\"`\n - `\"ReadOnly\"`\n - `\"ReadWrite\"`")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private CachingMode cachingMode;

    public CachingMode getCachingMode() {
        return cachingMode;
    }

    public void setCachingMode(CachingMode cachingMode) {
        this.cachingMode = cachingMode;
    }

    /**
     * diskName is the Name of the data disk in the blob storage
     */
    @com.fasterxml.jackson.annotation.JsonProperty("diskName")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("diskName is the Name of the data disk in the blob storage")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String diskName;

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    /**
     * diskURI is the URI of data disk in the blob storage
     */
    @com.fasterxml.jackson.annotation.JsonProperty("diskURI")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("diskURI is the URI of data disk in the blob storage")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String diskURI;

    public String getDiskURI() {
        return diskURI;
    }

    public void setDiskURI(String diskURI) {
        this.diskURI = diskURI;
    }

    /**
     * fsType is Filesystem type to mount. Must be a filesystem type supported by the host operating system. Ex. "ext4", "xfs", "ntfs". Implicitly inferred to be "ext4" if unspecified.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("fsType")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("fsType is Filesystem type to mount. Must be a filesystem type supported by the host operating system. Ex. \"ext4\", \"xfs\", \"ntfs\". Implicitly inferred to be \"ext4\" if unspecified.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String fsType;

    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
    }

    public enum Kind {

        @com.fasterxml.jackson.annotation.JsonProperty("Dedicated")
        DEDICATED("Dedicated"), @com.fasterxml.jackson.annotation.JsonProperty("Managed")
        MANAGED("Managed"), @com.fasterxml.jackson.annotation.JsonProperty("Shared")
        SHARED("Shared");

        java.lang.String value;

        Kind(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    /**
     * kind expected values are Shared: multiple blob disks per storage account  Dedicated: single blob disk per storage account  Managed: azure managed data disk (only in managed availability set). defaults to shared
     *
     * Possible enum values:
     *  - `"Dedicated"`
     *  - `"Managed"`
     *  - `"Shared"`
     */
    @com.fasterxml.jackson.annotation.JsonProperty("kind")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("kind expected values are Shared: multiple blob disks per storage account  Dedicated: single blob disk per storage account  Managed: azure managed data disk (only in managed availability set). defaults to shared\n\nPossible enum values:\n - `\"Dedicated\"`\n - `\"Managed\"`\n - `\"Shared\"`")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Kind kind;

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    /**
     * readOnly Defaults to false (read/write). ReadOnly here will force the ReadOnly setting in VolumeMounts.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("readOnly")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("readOnly Defaults to false (read/write). ReadOnly here will force the ReadOnly setting in VolumeMounts.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Boolean readOnly;

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }
}

