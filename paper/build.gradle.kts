import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask

plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.jsonschema2pojo)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://nexus.runicrealms.com/repository/maven-releases/")
}

dependencies {
    implementation(project(":common"))

    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.test)

    // Configuration and Injection
    implementation(libs.guice)
    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.dataformat.yaml)
    implementation(libs.jsonschema2pojo)
    implementation(libs.hibernate)
    implementation(libs.jakarta.validation)
    implementation(libs.expressly)

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

tasks.withType(KaptGenerateStubsTask::class.java).configureEach {
    dependsOn("generateJsonSchema2Pojo")
}

tasks.shadowJar {
    archiveBaseName.set("velagones-paper")
    mergeServiceFiles() // Necessary because of something to do with gRPC managed channels
}

jsonSchema2Pojo {
    setSource(files("src/main/resources/config.schema.json"))
    targetPackage = "com.runicrealms.velagones.paper.config"
    useTitleAsClassname = true
    removeOldOutput = true
    initializeCollections = true
    classNameSuffix = "Config"
}
