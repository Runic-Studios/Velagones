import com.google.protobuf.gradle.*

plugins { id("com.google.protobuf") version "0.9.4" }

buildscript {
    repositories {
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
    dependencies { classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.4") }
}

repositories { mavenCentral() }

dependencies {
    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")

    // For config
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.18.2")
    implementation("io.swagger.parser.v3:swagger-parser:2.1.24")

    // Protobuf
    implementation("io.grpc:grpc-stub:1.64.0")
    implementation("io.grpc:grpc-protobuf:1.64.0")
    implementation("io.grpc:grpc-okhttp:1.68.0")
    implementation("com.google.protobuf:protobuf-kotlin:4.28.3")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
}

tasks.withType<Test> { useJUnitPlatform() }

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:4.29.2" }
    plugins { id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:1.69.0" } }
    generateProtoTasks { ofSourceSet("main").configureEach { plugins { id("grpc") {} } } }
}

sourceSets {
    main { java { srcDir("${layout.buildDirectory.get()}/generated/source/proto/main/java") } }
}

tasks.named("compileKotlin") { dependsOn("generateProto") }
