import com.ncorti.ktfmt.gradle.tasks.KtfmtCheckTask
import com.ncorti.ktfmt.gradle.tasks.KtfmtFormatTask

plugins {
    kotlin("jvm") version "2.1.0"
    id("com.ncorti.ktfmt.gradle") version "0.21.0"
}

allprojects {
    repositories { mavenCentral() }

    apply(plugin = "org.jetbrains.kotlin.jvm")

    kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

    java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }
}

ktfmt {
    kotlinLangStyle()
    srcSetPathExclusionPattern = Regex(".*generated.*")
}

tasks.withType<KtfmtFormatTask>().configureEach {
    source = project.fileTree(rootDir)
    include("**/*.kt")
}

tasks.withType<KtfmtCheckTask>().configureEach {
    source = project.fileTree(rootDir)
    include("**/*.kt")
}
