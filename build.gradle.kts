buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.7.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.4")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Libs.kotlinVersion}")
//        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Libs.navigationVersion}")
//        classpath("com.google.dagger:hilt-android-gradle-plugin:${Libs.daggerHiltVersion}")
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.11.0.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.layout.buildDirectory)
}

plugins {
    // https://github.com/google/ksp/releases
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    // https://developer.android.com/develop/ui/compose/compiler
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
}
