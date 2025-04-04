import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.fabric8.gen)
    alias(libs.plugins.protobuf)
}

dependencies {
    implementation(libs.fabric8.kubernetes.client)
    implementation(libs.fabric8.generator.annotations)

    implementation(libs.protobuf.kotlin)
    implementation(libs.grpc.stub)
    implementation(libs.grpc.netty)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.kotlinstub)
    implementation(libs.tomcat.annotations)
}

javaGen {
    urls.add(
        "https://raw.githubusercontent.com/googleforgames/agones/release-1.47.0/install/yaml/install.yaml"
    )
}

sourceSets {
    main { java { srcDir(File(layout.buildDirectory.asFile.get(), "generated/sources/")) } }
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:4.30.1" }
    plugins {
        id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:1.71.0" }
        id("grpckt") { artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar" }
    }
    generateProtoTasks {
        base
        all().configureEach {
            plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

tasks.named("compileJava") {
    dependsOn("generateProto")
    dependsOn("crd2java")
}
