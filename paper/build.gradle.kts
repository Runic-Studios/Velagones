plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.protobuf)
    `maven-publish`
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://reposilite.runicrealms.com/releases/")
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

tasks.shadowJar {
    archiveBaseName.set("velagones-paper")
    mergeServiceFiles() // Necessary because of something to do with gRPC managed channels
    relocate("com.google.protobuf", "shadow.com.google.protobuf")
    relocate("com.fasterxml.jackson", "shadow.com.fasterxml.jackson")
}

val archiveName = "velagones-paper"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.parent!!.group.toString()
            artifactId = archiveName
            version = project.parent!!.version.toString()
        }
    }
    repositories {
        maven {
            name = "nexus"
            url = uri("https://reposilite.runicrealms.com/releases/")
            credentials {
                username = System.getenv("REPOSILITE_USERNAME")
                password = System.getenv("REPOSILITE_PASSWORD")
            }
        }
    }
}