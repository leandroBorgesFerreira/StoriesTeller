import com.android.build.gradle.internal.packaging.defaultExcludes
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.graalvm.buildtools.native") version "0.9.24"
}

group = "io.storiesteller.sdk.intronotes"
version = "0.0.35-SNAPSHOT"

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(project(":storiesteller_serialization"))

    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.2")

    implementation(platform("software.amazon.awssdk:bom:2.20.56"))
    implementation("software.amazon.awssdk:dynamodb-enhanced")
    implementation(project(":storiesteller_models"))

    testImplementation(libs.junit)
    testImplementation("com.amazonaws:DynamoDBLocal:2.0.0")
    testImplementation("io.github.ganadist.sqlite4java:libsqlite4java-osx-arm64:1.0.392")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.withType<Test> {
    if (System.getenv("isCI") == "true") {
        exclude("**/integrationTests/**")
    }
}

graalvmNative {
    binaries {
        named("main") {
//            runtimeArgs.add("--initialize-at-run-time=org.slf4j")
            mainClass.set("io.storiesteller.sdk.intronotes.MainKt")
        }
    }
}