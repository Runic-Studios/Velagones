plugins { id("io.fabric8.java-generator") version "7.1.0" }

dependencies {
    implementation("io.fabric8:kubernetes-client:7.1.0")
    implementation("io.fabric8:generator-annotations:7.1.0")
}

javaGen {
    urls.add(
        "https://raw.githubusercontent.com/googleforgames/agones/release-1.47.0/install/yaml/install.yaml"
    )
    target.set(File(projectDir, "generated/src/main/java"))
}

sourceSets { main { java { srcDir("generated/src/main/java") } } }
