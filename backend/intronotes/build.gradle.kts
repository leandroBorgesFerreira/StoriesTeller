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
}

group = "com.github.leandroborgesferreira.storyteller.intronotes"
version = "0.0.21-SNAPSHOT"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation(project(":storyteller_serialization"))

    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.2")

    implementation(platform("software.amazon.awssdk:bom:2.20.56"))
    implementation("software.amazon.awssdk:dynamodb-enhanced")
    implementation(project(":storyteller_models"))

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}