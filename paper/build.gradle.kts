plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.protobuf)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":common"))

    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.test)

    // Configuration and Injection
    implementation(libs.reflections)
    implementation(libs.guice)
    implementation(libs.typesafe.config)

    // PaperMC
    compileOnly(libs.paper.api)

    // Fabric8 Kubernetes Client
    implementation(libs.fabric8.kubernetes.client)
    implementation(libs.fabric8.generator.annotations)

    // Agones client SDK
    implementation(libs.agones4j)

    // gRPC + Protobuf
    implementation(libs.protobuf.kotlin)
    implementation(libs.grpc.stub)
    implementation(libs.grpc.netty)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.kotlinstub)
    implementation(libs.tomcat.annotations)
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.build { dependsOn("shadowJar") }

tasks.shadowJar { archiveBaseName.set("velagones-paper") }
