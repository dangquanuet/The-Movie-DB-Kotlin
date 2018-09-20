import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

androidExtensions {
    configure(delegateClosureOf<AndroidExtensionsExtension> {
        isExperimental = true
    })
}

kotlin {
    // configure<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension>
    experimental.coroutines = Coroutines.ENABLE
}

android {

    defaultConfig {
        applicationId = "com.quanda.moviedb"
        minSdkVersion(19)
        compileSdkVersion(28)
        targetSdkVersion(28)
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = File("movie-db.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs["debug"]
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            isUseProguard = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            signingConfig = signingConfigs["release"]
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            isUseProguard = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        setTargetCompatibility(1.8)
        setSourceCompatibility(1.8)
    }

    dataBinding {
        isEnabled = true
    }

    flavorDimensions("default")

    productFlavors {
        create("dev") {
            versionCode = 1
            versionName = "1.0.0"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Movie DB Dev")
        }
        create("prd") {
            versionCode = 1
            versionName = "1.0.0"
            resValue("string", "app_name", "Movie DB")
        }
    }

    applicationVariants.all {
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/\"")
        buildConfigField("String", "SMALL_IMAGE_URL", "\"https://image.tmdb.org/t/p/w200\"")
        buildConfigField("String", "LARGE_IMAGE_URL", "\"https://image.tmdb.org/t/p/w500\"")
        buildConfigField("String", "ORIGINAL_IMAGE_URL", "\"https://image.tmdb.org/t/p/original\"")
        buildConfigField("String", "TMBD_API_KEY", "\"2cdf3a5c7cf412421485f89ace91e373\"")

        when (this.name) {
            "dev" -> {
            }
            "prd" -> {
            }
        }
    }
}

dependencies {
    implementation("com.android.support:appcompat-v7:28.0.0-rc02")
    implementation("com.android.support:support-v4:28.0.0-rc02")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    implementation("com.android.support:recyclerview-v7:28.0.0-rc02")
    implementation("com.android.support:cardview-v7:28.0.0-rc02")
    implementation("com.android.support:design:28.0.0-rc02")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinCompilerVersion.VERSION}")

    //Support ReactiveX android
    implementation("io.reactivex.rxjava2:rxjava:2.1.17")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.2")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.22.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:0.22.5")

    //Using this for parse json data to object
    implementation("com.google.code.gson:gson:2.8.5")

    //Using retrofit library for connect api v4.data.source.remote.service
    implementation("com.squareup.retrofit2:retrofit:2.4.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.9.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.4.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-experimental-adapter:1.0.0")

    //Glide library load image
    implementation("com.github.bumptech.glide:glide:4.7.1") {
        exclude("module", "support-annotations")
    }
    kapt("com.github.bumptech.glide:compiler:4.8.0")

    // Koin for Kotlin
    implementation("org.koin:koin-core:1.0.0")
    // Koin for Android
    implementation("org.koin:koin-android:1.0.0")
    // Koin Android ViewModel feature
    implementation("org.koin:koin-android-viewmodel:1.0.0")

    // bottom navigation
    implementation("com.aurelhubert:ahbottomnavigation:2.1.0")

    // databinding compiler
    kapt("com.android.databinding:compiler:3.1.4")

    // room
    implementation("android.arch.persistence.room:runtime:1.1.1")
    kapt("android.arch.persistence.room:compiler:1.1.1")
    // RxJava support for Room
    implementation("android.arch.persistence.room:rxjava2:1.1.1")

    // ViewModel and LiveData
    implementation("android.arch.lifecycle:extensions:1.1.1")
    kapt("android.arch.lifecycle:compiler:1.1.1")
    // Java8 support for Lifecycles
    implementation("android.arch.lifecycle:common-java8:1.1.1")
    // ReactiveStreams support for LiveData
    implementation("android.arch.lifecycle:reactivestreams:1.1.1")

    // unit test
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.19.0")
    testImplementation("android.arch.core:core-testing:1.1.1")

    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    androidTestImplementation("android.arch.persistence.room:testing:1.1.1")
    androidTestImplementation("android.arch.core:core-testing:1.1.1")

    testImplementation("com.squareup.okhttp3:mockwebserver:3.9.0")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:${KotlinCompilerVersion.VERSION}")
}

/*
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'
apply plugin: 'org.jetbrains.kotlin.android.extensions'
androidExtensions {
    experimental = true
}

kotlin {
    experimental {
        coroutines 'enable'
    }
}

android {
    compileSdkVersion 27
    buildToolsVersion "27.0.3"

    defaultConfig {
        applicationId "com.quanda.moviedb"
        minSdkVersion 19
        targetSdkVersion 28
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        release {
            storeFile file("movie-db.jks")
            storePassword "123456"
            keyAlias "key0"
            keyPassword "123456"
        }
    }

    buildTypes {
        debug {
            signingConfig signingConfigs.debug
            buildConfigField "boolean", "MOCK_DATA", "false"
            debuggable true
            minifyEnabled false
            shrinkResources false
            useProguard false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }

        mock {
            signingConfig signingConfigs.debug
            buildConfigField "boolean", "MOCK_DATA", "true"
            debuggable true
            minifyEnabled false
            shrinkResources false
            useProguard false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }

        release {
            signingConfig signingConfigs.release
            buildConfigField "boolean", "MOCK_DATA", "false"
            debuggable false
            minifyEnabled true
            shrinkResources true
            useProguard true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        targetCompatibility 1.8
        sourceCompatibility 1.8
    }

    dataBinding {
        enabled = true
    }

    flavorDimensions "default"

    productFlavors {
        dev {
            versionCode rootProject.ext.versionCode_dev
            versionName rootProject.ext.versionName_dev
            applicationIdSuffix = ".dev"
            resValue "string", "app_name", "Movie DB DEV"
        }

        // build this flavor for qa test
        stg {
            versionCode rootProject.ext.versionCode_dev
            versionName rootProject.ext.versionName_dev
            applicationIdSuffix = ".stg"
            resValue "string", "app_name", "Movie DB STG"
        }

        prd {
            versionCode rootProject.ext.versionCode_product
            versionName rootProject.ext.versionName_product
            resValue "string", "app_name", "Movie DB"
        }
    }

    productFlavors.all {}

    applicationVariants.all { variant ->
        def BASE_URL = "https://api.themoviedb.org/"
        def SMALL_IMAGE_URL = "https://image.tmdb.org/t/p/w200"
        def LARGE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        def ORIGINAL_IMAGE_URL = "https://image.tmdb.org/t/p/original"
        def TMBD_API_KEY = "2cdf3a5c7cf412421485f89ace91e373"

        //    if (variant.getName().contains("dev")) {
        //      BASE_URL = "https://api.themoviedb.org/"
        //    } else if (variant.getName().contains("stg")) {
        //      BASE_URL = "https://api.themoviedb.org/"
        //    } else if (variant.getName().contains("prd")) {
        //      BASE_URL = "https://api.themoviedb.org/"
        //    }

        variant.buildConfigField "String", "BASE_URL", "\"${BASE_URL}\""
        variant.buildConfigField "String", "SMALL_IMAGE_URL", "\"${SMALL_IMAGE_URL}\""
        variant.buildConfigField "String", "LARGE_IMAGE_URL", "\"${LARGE_IMAGE_URL}\""
        variant.buildConfigField "String", "ORIGINAL_IMAGE_URL", "\"${ORIGINAL_IMAGE_URL}\""
        variant.buildConfigField "String", "TMBD_API_KEY", "\"${TMBD_API_KEY}\""
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'com.android.support:appcompat-v7:27.1.1'
    implementation "com.android.support:support-v4:27.1.1"
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    implementation "com.android.support:recyclerview-v7:27.1.1"
    implementation "com.android.support:cardview-v7:27.1.1"
    implementation "com.android.support:design:27.1.1"

    //Support ReactiveX android
    implementation 'io.reactivex.rxjava2:rxjava:2.1.17'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'

    // coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:0.22.5"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:0.22.5"

    //Using this for parse json data to object
    implementation "com.google.code.gson:gson:2.8.5"

    //Using retrofit library for connect api v4.data.source.remote.service
    implementation "com.squareup.retrofit2:retrofit:2.4.0"
    implementation "com.squareup.retrofit2:converter-gson:2.4.0"
    implementation "com.squareup.okhttp3:logging-interceptor:3.9.1"
    implementation "com.squareup.retrofit2:adapter-rxjava2:2.4.0"
    implementation 'com.jakewharton.retrofit:retrofit2-kotlin-coroutines-experimental-adapter:1.0.0'

    //Glide library load image
    implementation("com.github.bumptech.glide:glide:4.7.1") {
        exclude module: 'support-annotations'
    }
    kapt "com.github.bumptech.glide:compiler:4.7.1"

    //dagger 2 core
    implementation "com.google.dagger:dagger:2.16"
    kapt "com.google.dagger:dagger-compiler:2.16"
    compileOnly 'javax.annotation:jsr250-api:1.0'
    implementation 'javax.inject:javax.inject:1'

    // dagger 2 android
    implementation "com.google.dagger:dagger-android:2.16"
    implementation "com.google.dagger:dagger-android-support:2.16"
    kapt "com.google.dagger:dagger-android-processor:2.16"

    // bottom navigation
    implementation 'com.aurelhubert:ahbottomnavigation:2.1.0'

    // databinding compiler
    kapt 'com.android.databinding:compiler:3.1.4'

    // room
    implementation "android.arch.persistence.room:runtime:1.1.1"
    kapt "android.arch.persistence.room:compiler:1.1.1"
    // RxJava support for Room
    implementation "android.arch.persistence.room:rxjava2:1.1.1"

    // ViewModel and LiveData
    implementation "android.arch.lifecycle:extensions:1.1.1"
    //  kapt "android.arch.lifecycle:compiler:1.1.1"
    // Java8 support for Lifecycles
    implementation "android.arch.lifecycle:common-java8:1.1.1"
    // ReactiveStreams support for LiveData
    implementation "android.arch.lifecycle:reactivestreams:1.1.1"

    // Paging
    implementation "android.arch.paging:runtime:1.0.1"
    // RxJava support for Paging
    implementation "android.arch.paging:rxjava2:1.0.1"

    // unit test
    testImplementation 'junit:junit:4.12'
    testImplementation 'org.mockito:mockito-core:2.19.0'
    testImplementation "android.arch.core:core-testing:1.1.1"

    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

    androidTestImplementation "android.arch.persistence.room:testing:1.1.1"
    androidTestImplementation "android.arch.core:core-testing:1.1.1"

    testImplementation "com.squareup.okhttp3:mockwebserver:3.9.0"
    testImplementation "org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version"
}
*/