import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask

plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.openapi)
    alias(libs.plugins.kotlin.kapt)
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

    // Velocity
    kapt(libs.velocity.api)
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)

    // Fabric8 Kubernetes Client
    implementation(libs.fabric8.kubernetes.client)
    implementation(libs.fabric8.generator.annotations)

    // gRPC + Protobuf
    implementation(libs.protobuf.kotlin)
    implementation(libs.grpc.stub)
    implementation(libs.grpc.netty)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.kotlinstub)
    implementation(libs.tomcat.annotations)
    implementation(libs.coroutines)

    // Javalin for Agones Autoscaler
    implementation(libs.javalin)
    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.databind)

    // For OpenAPI
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gsonfire)
}

openApiGenerate {
    generatorName.set("java")
    inputSpec.set("${layout.projectDirectory}/src/main/resources/openapi.yaml")
    outputDir.set("${layout.buildDirectory.get()}/generated")
    modelPackage.set("com.runicrealms.velagones.velocity.generated.autoscaler.model")
    apiPackage.set("com.runicrealms.velagones.velocity.generated.autoscaler.api")
    invokerPackage.set("com.runicrealms.velagones.velocity.generated.autoscaler.invoker")
}

sourceSets { main { java { srcDir("${layout.buildDirectory.get()}/generated/src/main/java") } } }

tasks.withType<Test> { useJUnitPlatform() }

tasks.build { dependsOn("shadowJar") }

tasks.named("compileJava") { dependsOn("openApiGenerate") }

tasks.withType(KaptGenerateStubsTask::class.java).configureEach { dependsOn("openApiGenerate") }

tasks.shadowJar {
    archiveBaseName.set("velagones-velocity")
    mergeServiceFiles() // Necessary because of something to do with gRPC managed channels
}
