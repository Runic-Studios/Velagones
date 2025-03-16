package dev.agones.v1;

@io.fabric8.kubernetes.model.annotation.Version(value = "v1" , storage = true , served = true)
@io.fabric8.kubernetes.model.annotation.Group("agones.dev")
@io.fabric8.kubernetes.model.annotation.Singular("gameserver")
@io.fabric8.kubernetes.model.annotation.Plural("gameservers")
@javax.annotation.processing.Generated("io.fabric8.java.generator.CRGeneratorRunner")
public class GameServer extends io.fabric8.kubernetes.client.CustomResource<dev.agones.v1.GameServerSpec, dev.agones.v1.GameServerStatus> implements io.fabric8.kubernetes.api.model.Namespaced {

    @java.lang.Override()
    @io.fabric8.generator.annotation.Required()
    public dev.agones.v1.GameServerSpec getSpec() {
        return super.getSpec();
    }
}

