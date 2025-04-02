import com.ncorti.ktfmt.gradle.tasks.KtfmtCheckTask
import com.ncorti.ktfmt.gradle.tasks.KtfmtFormatTask

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ktfmt)
}

group = "com.runicrealms.velagones"

version = "0.0.1-SNAPSHOT"

repositories { mavenCentral() }

subprojects {
    repositories { mavenCentral() }

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")

    kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

    java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }
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
