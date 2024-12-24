plugins { id("org.openapi.generator") version "7.9.0" }

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Common
    implementation(project(":common"))

    // Guice Dependency
    implementation("com.google.inject:guice:7.0.0")

    // Paper Dependency
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

    // Logging
    implementation("com.djaytan.bukkit:bukkit-slf4j:2.0.0")

    // Kubernetes + Agones
    implementation("io.kubernetes:client-java:21.0.2")
    implementation("net.infumia:agones4j:2.0.2")

    // GRPC + Protobuf
    implementation("io.grpc:grpc-stub:1.64.0")
    implementation("io.grpc:grpc-protobuf:1.64.0")
    implementation("io.grpc:grpc-okhttp:1.68.0")
    implementation("com.google.protobuf:protobuf-kotlin:4.28.3")

    // For config
    implementation("jakarta.validation:jakarta.validation-api:3.1.0")

    // Testing Dependencies
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // Await futures
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
}

openApiGenerate {
    generatorName.set("java")
    inputSpec.set("${layout.projectDirectory}/src/main/resources/config-spec.yml")
    outputDir.set("${layout.buildDirectory.get()}/generated")
    modelPackage.set("com.runicrealms.velagones.paper.generated.model")
    apiPackage.set("com.runicrealms.velagones.paper.generated.api")
    invokerPackage.set("com.runicrealms.velagones.paper.generated.invoker")

    configOptions.put("useJakartaEe", "true")
    configOptions.put("useBeanValidation", "true")
}

sourceSets { main { java { srcDir("${layout.buildDirectory.get()}/generated/src/main/java") } } }

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

tasks.withType<Test> { useJUnitPlatform() }

tasks.named("compileKotlin") { dependsOn("openApiGenerate") }
