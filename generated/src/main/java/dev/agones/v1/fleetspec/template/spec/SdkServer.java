package dev.agones.v1.fleetspec.template.spec;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"grpcPort","httpPort","logLevel"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class SdkServer implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * Starting with Agones 1.2 the default gRPC port is 9357. In earlier releases, the default was 59357.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("grpcPort")
    @io.fabric8.generator.annotation.Max(65535.0)
    @io.fabric8.generator.annotation.Min(1.0)
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Starting with Agones 1.2 the default gRPC port is 9357. In earlier releases, the default was 59357.\n")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long grpcPort;

    public Long getGrpcPort() {
        return grpcPort;
    }

    public void setGrpcPort(Long grpcPort) {
        this.grpcPort = grpcPort;
    }

    /**
     * Starting with Agones 1.2 the default HTTP port is 9358. In earlier releases, the default was 59358.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("httpPort")
    @io.fabric8.generator.annotation.Max(65535.0)
    @io.fabric8.generator.annotation.Min(1.0)
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Starting with Agones 1.2 the default HTTP port is 9358. In earlier releases, the default was 59358.\n")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Long httpPort;

    public Long getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Long httpPort) {
        this.httpPort = httpPort;
    }

    public enum LogLevel {

        @com.fasterxml.jackson.annotation.JsonProperty("Error")
        ERROR("Error"), @com.fasterxml.jackson.annotation.JsonProperty("Info")
        INFO("Info"), @com.fasterxml.jackson.annotation.JsonProperty("Debug")
        DEBUG("Debug"), @com.fasterxml.jackson.annotation.JsonProperty("Trace")
        TRACE("Trace");

        java.lang.String value;

        LogLevel(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    /**
     * sdkServer log level parameter has three options:
     * - "Info" (default) The SDK server will output all messages except for debug messages
     * - "Debug" The SDK server will output all messages including debug messages
     * - "Error" The SDK server will only output error messages
     * - "Trace" The SDK server will output all messages, including detailed tracing information
     */
    @com.fasterxml.jackson.annotation.JsonProperty("logLevel")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("sdkServer log level parameter has three options:\n- \"Info\" (default) The SDK server will output all messages except for debug messages\n- \"Debug\" The SDK server will output all messages including debug messages\n- \"Error\" The SDK server will only output error messages\n- \"Trace\" The SDK server will output all messages, including detailed tracing information\n")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private LogLevel logLevel;

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }
}

