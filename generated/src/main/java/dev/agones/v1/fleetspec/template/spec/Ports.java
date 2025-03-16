package dev.agones.v1.fleetspec.template.spec;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"container","containerPort","hostPort","name","portPolicy","protocol","range"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Ports implements io.fabric8.kubernetes.api.model.KubernetesResource {

    @com.fasterxml.jackson.annotation.JsonProperty("container")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String container;

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("containerPort")
    @io.fabric8.generator.annotation.Max(65535.0)
    @io.fabric8.generator.annotation.Min(1.0)
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long containerPort;

    public Long getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(Long containerPort) {
        this.containerPort = containerPort;
    }

    /**
     * Only required when `portPolicy` is "Static". Overwritten when portPolicy is "Dynamic" or "Passthrough".
     */
    @com.fasterxml.jackson.annotation.JsonProperty("hostPort")
    @io.fabric8.generator.annotation.Max(65535.0)
    @io.fabric8.generator.annotation.Min(1.0)
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Only required when `portPolicy` is \"Static\". Overwritten when portPolicy is \"Dynamic\" or \"Passthrough\".")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long hostPort;

    public Long getHostPort() {
        return hostPort;
    }

    public void setHostPort(Long hostPort) {
        this.hostPort = hostPort;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum PortPolicy {

        @com.fasterxml.jackson.annotation.JsonProperty("Dynamic")
        DYNAMIC("Dynamic"), @com.fasterxml.jackson.annotation.JsonProperty("Static")
        STATIC("Static"), @com.fasterxml.jackson.annotation.JsonProperty("Passthrough")
        PASSTHROUGH("Passthrough"), @com.fasterxml.jackson.annotation.JsonProperty("None")
        NONE("None");

        java.lang.String value;

        PortPolicy(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    /**
     * portPolicy has four options:
     * - "Dynamic" (default) the system allocates a random free hostPort for the gameserver, for game clients to connect to
     * - "Static", user defines the hostPort that the game client will connect to. Then onus is on the user to ensure that the
     * port is available. When static is the policy specified, `hostPort` is required to be populated
     * - "Passthrough" dynamically sets the `containerPort` to the same value as the dynamically selected hostPort.
     * This will mean that users will need to lookup what port has been opened through the server side SDK.
     * - "None" means the `hostPort` is ignored and if defined, the `containerPort` (optional) is used to set the port on the GameServer instance.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("portPolicy")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("portPolicy has four options:\n- \"Dynamic\" (default) the system allocates a random free hostPort for the gameserver, for game clients to connect to\n- \"Static\", user defines the hostPort that the game client will connect to. Then onus is on the user to ensure that the\nport is available. When static is the policy specified, `hostPort` is required to be populated\n- \"Passthrough\" dynamically sets the `containerPort` to the same value as the dynamically selected hostPort.\nThis will mean that users will need to lookup what port has been opened through the server side SDK.\n- \"None\" means the `hostPort` is ignored and if defined, the `containerPort` (optional) is used to set the port on the GameServer instance.\n")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private PortPolicy portPolicy;

    public PortPolicy getPortPolicy() {
        return portPolicy;
    }

    public void setPortPolicy(PortPolicy portPolicy) {
        this.portPolicy = portPolicy;
    }

    public enum Protocol {

        @com.fasterxml.jackson.annotation.JsonProperty("UDP")
        UDP("UDP"), @com.fasterxml.jackson.annotation.JsonProperty("TCP")
        TCP("TCP"), @com.fasterxml.jackson.annotation.JsonProperty("TCPUDP")
        TCPUDP("TCPUDP");

        java.lang.String value;

        Protocol(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    @com.fasterxml.jackson.annotation.JsonProperty("protocol")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Protocol protocol;

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("range")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String range;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}

