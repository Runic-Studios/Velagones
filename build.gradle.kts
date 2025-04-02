import com.ncorti.ktfmt.gradle.tasks.KtfmtCheckTask
import com.ncorti.ktfmt.gradle.tasks.KtfmtFormatTask

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.ncorti.ktfmt.gradle") version "0.21.0"
    kotlin("kapt") version "2.1.10"
    id("com.gradleup.shadow") version "8.3.6"
    id("io.fabric8.java-generator") version "7.1.0"
}

group = "com.runicrealms"

version = "0.0.1-SNAPSHOT"

java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.25")
    implementation("org.jetbrains.kotlin:kotlin-test:1.9.25")

    kapt("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")

    implementation("io.kubernetes:client-java:21.0.2")

    implementation("io.fabric8:kubernetes-client:7.1.0")
    implementation("io.fabric8:generator-annotations:7.1.0")
}

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

tasks.withType<Test> { useJUnitPlatform() }

ktfmt {
    kotlinLangStyle()
    srcSetPathExclusionPattern = Regex(".*generated.*")
}

tasks.withType<KtfmtFormatTask>().configureEach {
    source = project.fileTree(rootDir)
    include("**/*.kt")
    dependsOn("processResources")
}

tasks.withType<KtfmtCheckTask>().configureEach {
    source = project.fileTree(rootDir)
    include("**/*.kt")
    dependsOn("processResources")
}

tasks.bootJar { enabled = false }

tasks.jar { manifest { attributes["Spring-Boot-Jar-Type"] = "thin" } }

tasks.build { dependsOn("shadowJar") }

javaGen {
    urls.add(
        "https://raw.githubusercontent.com/googleforgames/agones/release-1.47.0/install/yaml/install.yaml"
    )
    target.set(File(projectDir, "generated/src/main/java"))
}

sourceSets { main { java { srcDir("generated/src/main/java") } } }
