plugins { id("com.gradleup.shadow") version "8.3.6" }

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":common"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.test)

    implementation(libs.reflections)
    implementation(libs.guice)

    implementation(libs.typesafe.config)

    kapt(libs.velocity.api)
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)

    implementation(libs.kubernetes.client)
    implementation(libs.fabric8.kubernetes.client)
    implementation(libs.fabric8.generator.annotations)
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.build { dependsOn("shadowJar") }

tasks.shadowJar {
    archiveBaseName.set("velagones-velocity")
}