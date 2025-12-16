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
        "https://raw.githubusercontent.com/googleforgames/agones/release-1.54.0/install/yaml/install.yaml"
    )
}

sourceSets {
    main { java { srcDir(File(layout.buildDirectory.asFile.get(), "generated/sources/")) } }
}

val protobufVersion = libs.versions.protobuf.get()
val grpcVersion = libs.versions.grpc.get()
val grpcKotlinVersion = libs.versions.grpcKotlin.get()

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:$protobufVersion" }
    plugins {
        id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion" }
        id("grpckt") { artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar" }
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
