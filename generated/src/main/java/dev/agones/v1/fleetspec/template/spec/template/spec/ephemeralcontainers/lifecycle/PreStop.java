package dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"exec","httpGet","sleep","tcpSocket"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class PreStop implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * Exec specifies the action to take.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("exec")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Exec specifies the action to take.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.Exec exec;

    public dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.Exec getExec() {
        return exec;
    }

    public void setExec(dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.Exec exec) {
        this.exec = exec;
    }

    /**
     * HTTPGet specifies the http request to perform.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("httpGet")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("HTTPGet specifies the http request to perform.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.HttpGet httpGet;

    public dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.HttpGet getHttpGet() {
        return httpGet;
    }

    public void setHttpGet(dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.HttpGet httpGet) {
        this.httpGet = httpGet;
    }

    /**
     * Sleep represents the duration that the container should sleep before being terminated.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("sleep")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Sleep represents the duration that the container should sleep before being terminated.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.Sleep sleep;

    public dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.Sleep getSleep() {
        return sleep;
    }

    public void setSleep(dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.Sleep sleep) {
        this.sleep = sleep;
    }

    /**
     * Deprecated. TCPSocket is NOT supported as a LifecycleHandler and kept for the backward compatibility. There are no validation of this field and lifecycle hooks will fail in runtime when tcp handler is specified.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("tcpSocket")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("Deprecated. TCPSocket is NOT supported as a LifecycleHandler and kept for the backward compatibility. There are no validation of this field and lifecycle hooks will fail in runtime when tcp handler is specified.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.TcpSocket tcpSocket;

    public dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.TcpSocket getTcpSocket() {
        return tcpSocket;
    }

    public void setTcpSocket(dev.agones.v1.fleetspec.template.spec.template.spec.ephemeralcontainers.lifecycle.prestop.TcpSocket tcpSocket) {
        this.tcpSocket = tcpSocket;
    }
}

