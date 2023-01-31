buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.google.com")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Libs.kotlinVersion}")
//        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Libs.navigationVersion}")
//        classpath("com.google.dagger:hilt-android-gradle-plugin:${Libs.daggerHiltVersion}")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.4")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.google.com")
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
