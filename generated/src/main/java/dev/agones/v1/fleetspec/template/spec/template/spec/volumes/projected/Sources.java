package dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected;

@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonPropertyOrder({"clusterTrustBundle","configMap","downwardAPI","secret","serviceAccountToken"})
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class Sources implements io.fabric8.kubernetes.api.model.KubernetesResource {

    /**
     * ClusterTrustBundle allows a pod to access the `.spec.trustBundle` field of ClusterTrustBundle objects in an auto-updating file.
     *
     * Alpha, gated by the ClusterTrustBundleProjection feature gate.
     *
     * ClusterTrustBundle objects can either be selected by name, or by the combination of signer name and a label selector.
     *
     * Kubelet performs aggressive normalization of the PEM contents written into the pod filesystem.  Esoteric PEM features such as inter-block comments and block headers are stripped.  Certificates are deduplicated. The ordering of certificates within the file is arbitrary, and Kubelet may change the order over time.
     */
    @com.fasterxml.jackson.annotation.JsonProperty("clusterTrustBundle")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("ClusterTrustBundle allows a pod to access the `.spec.trustBundle` field of ClusterTrustBundle objects in an auto-updating file.\n\nAlpha, gated by the ClusterTrustBundleProjection feature gate.\n\nClusterTrustBundle objects can either be selected by name, or by the combination of signer name and a label selector.\n\nKubelet performs aggressive normalization of the PEM contents written into the pod filesystem.  Esoteric PEM features such as inter-block comments and block headers are stripped.  Certificates are deduplicated. The ordering of certificates within the file is arbitrary, and Kubelet may change the order over time.")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.ClusterTrustBundle clusterTrustBundle;

    public dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.ClusterTrustBundle getClusterTrustBundle() {
        return clusterTrustBundle;
    }

    public void setClusterTrustBundle(dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.ClusterTrustBundle clusterTrustBundle) {
        this.clusterTrustBundle = clusterTrustBundle;
    }

    /**
     * configMap information about the configMap data to project
     */
    @com.fasterxml.jackson.annotation.JsonProperty("configMap")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("configMap information about the configMap data to project")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.ConfigMap configMap;

    public dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.ConfigMap getConfigMap() {
        return configMap;
    }

    public void setConfigMap(dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.ConfigMap configMap) {
        this.configMap = configMap;
    }

    /**
     * downwardAPI information about the downwardAPI data to project
     */
    @com.fasterxml.jackson.annotation.JsonProperty("downwardAPI")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("downwardAPI information about the downwardAPI data to project")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.DownwardAPI downwardAPI;

    public dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.DownwardAPI getDownwardAPI() {
        return downwardAPI;
    }

    public void setDownwardAPI(dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.DownwardAPI downwardAPI) {
        this.downwardAPI = downwardAPI;
    }

    /**
     * secret information about the secret data to project
     */
    @com.fasterxml.jackson.annotation.JsonProperty("secret")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("secret information about the secret data to project")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.Secret secret;

    public dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.Secret getSecret() {
        return secret;
    }

    public void setSecret(dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.Secret secret) {
        this.secret = secret;
    }

    /**
     * serviceAccountToken is information about the serviceAccountToken data to project
     */
    @com.fasterxml.jackson.annotation.JsonProperty("serviceAccountToken")
    @com.fasterxml.jackson.annotation.JsonPropertyDescription("serviceAccountToken is information about the serviceAccountToken data to project")
    @com.fasterxml.jackson.annotation.JsonSetter(nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
    private dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.ServiceAccountToken serviceAccountToken;

    public dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.ServiceAccountToken getServiceAccountToken() {
        return serviceAccountToken;
    }

    public void setServiceAccountToken(dev.agones.v1.fleetspec.template.spec.template.spec.volumes.projected.sources.ServiceAccountToken serviceAccountToken) {
        this.serviceAccountToken = serviceAccountToken;
    }
}

