package dev.agones.v1.fleetspec.template.spec.template.spec.containers.lifecycle.poststart;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"host","httpHeaders","path","port","scheme"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class HttpGet implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * Host name to connect to, defaults to the pod IP. You probably want to set "Host" in httpHeaders instead.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("host")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Host name to connect to, defaults to the pod IP. You probably want to set \"Host\" in httpHeaders instead.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Custom headers to set in the request. HTTP allows repeated headers.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("httpHeaders")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Custom headers to set in the request. HTTP allows repeated headers.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private java.util.List<dev.agones.v1.fleetspec.template.spec.template.spec.containers.lifecycle.poststart.httpget.HttpHeaders> httpHeaders;

    public java.util.List<dev.agones.v1.fleetspec.template.spec.template.spec.containers.lifecycle.poststart.httpget.HttpHeaders> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(java.util.List<dev.agones.v1.fleetspec.template.spec.template.spec.containers.lifecycle.poststart.httpget.HttpHeaders> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    /**
     * Path to access on the HTTP server.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("path")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Path to access on the HTTP server.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Name or number of the port to access on the container. Number must be in the range 1 to 65535. Name must be an IANA_SVC_NAME.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("port")
    @io.fabric8.generator.annotation.Required()
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Name or number of the port to access on the container. Number must be in the range 1 to 65535. Name must be an IANA_SVC_NAME.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private io.fabric8.kubernetes.api.model.IntOrString port;

    public io.fabric8.kubernetes.api.model.IntOrString getPort() {
        return port;
    }

    public void setPort(io.fabric8.kubernetes.api.model.IntOrString port) {
        this.port = port;
    }

    public enum Scheme {

        @com.fasterxml.jackson.annotation.JsonProperty("HTTP")
        HTTP("HTTP"), @com.fasterxml.jackson.annotation.JsonProperty("HTTPS")
        HTTPS("HTTPS");

        java.lang.String value;

        Scheme(java.lang.String value) {
            this.value = value;
        }

        @com.fasterxml.jackson.annotation.JsonValue()
        public java.lang.String getValue() {
            return value;
        }
    }

    /**
     * Scheme to use for connecting to the host. Defaults to HTTP.
     *
     * Possible enum values:
     *  - `"HTTP"` means that the scheme used will be http://
     *  - `"HTTPS"` means that the scheme used will be https://
     */
    @com.fasterxml.jackson.annotation.JsonProperty("scheme")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Scheme to use for connecting to the host. Defaults to HTTP.\n\nPossible enum values:\n - `\"HTTP\"` means that the scheme used will be http://\n - `\"HTTPS\"` means that the scheme used will be https://")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private Scheme scheme;

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }
}

