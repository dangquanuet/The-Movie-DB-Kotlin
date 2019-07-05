import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

androidExtensions {
    configure<AndroidExtensionsExtension> {
        isExperimental = true
    }
}

android {

    defaultConfig {
        applicationId = "com.example.moviedb"
        minSdkVersion(17)
        compileSdkVersion(29)
        targetSdkVersion(29)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            isUseProguard = false
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            isUseProguard = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("default")

    productFlavors {
        create("dev") {
            versionCode = 1
            versionName = "1.0.0"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Movie DB Dev")
            buildConfigField("boolean", "MOCK_DATA", "false")
        }
        create("mock") {
            versionCode = 1
            versionName = "1.0.0"
            applicationIdSuffix = ".mock"
            resValue("string", "app_name", "Movie DB Mock")
            buildConfigField("boolean", "MOCK_DATA", "true")
        }
        create("prd") {
            versionCode = 1
            versionName = "1.0.0"
            resValue("string", "app_name", "Movie DB")
            buildConfigField("boolean", "MOCK_DATA", "false")
        }
    }

    applicationVariants.all {
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/\"")
        buildConfigField("String", "SMALL_IMAGE_URL", "\"https://image.tmdb.org/t/p/w200\"")
        buildConfigField("String", "LARGE_IMAGE_URL", "\"https://image.tmdb.org/t/p/w500\"")
        buildConfigField("String", "ORIGINAL_IMAGE_URL", "\"https://image.tmdb.org/t/p/original\"")
        buildConfigField("String", "TMBD_API_KEY", "\"2cdf3a5c7cf412421485f89ace91e373\"")

        when (name) {
            "dev" -> {
            }
            "prd" -> {
            }
        }
    }

    compileOptions {
        setTargetCompatibility(1.8)
        setSourceCompatibility(1.8)
    }

    dataBinding {
        isEnabled = true
    }

}

dependencies {
    // common
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinCompilerVersion.VERSION}")
    implementation("androidx.core:core-ktx:1.0.2")

    implementation("androidx.fragment:fragment-ktx:1.1.0-beta01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-alpha01")

    // ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation("androidx.lifecycle:lifecycle-common-java8:2.0.0")

    // room
    implementation("androidx.room:room-runtime:2.1.0")
    kapt("androidx.room:room-compiler:2.1.0")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.1.0")

    // paging
//    implementation("androidx.paging:paging-runtime-ktx:2.1.0")
    // alternatively - without Android dependencies for testing
//    testImplementation("androidx.paging:paging-common-ktx:2.1.0")
    // optional - RxJava support
//    implementation("androidx.paging:paging-rxjava2-ktx:2.1.0")

    // navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.0.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.0.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.0.0")

    // work manager
//    implementation("androidx.work:work-runtime-ktx:2.0.1")

    // rx
//    implementation("io.reactivex.rxjava2:rxjava:2.2.7")
//    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1")

    // gson
    implementation("com.google.code.gson:gson:2.8.5")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.0")
//    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.0")
//    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    // glide
    implementation("com.github.bumptech.glide:glide:4.9.0")
    kapt("com.github.bumptech.glide:compiler:4.9.0")

    // koin
//    implementation("org.koin:koin-core:2.0.1")
//    implementation("org.koin:koin-android:2.0.1")
    implementation("org.koin:koin-androidx-viewmodel:2.0.1")

    // runtime permission
//    implementation("pub.devrel:easypermissions:3.0.0")

    // unit test
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.25.1")
    testImplementation("android.arch.core:core-testing:1.1.1")

    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    androidTestImplementation("android.arch.persistence.room:testing:1.1.1")
    androidTestImplementation("android.arch.core:core-testing:1.1.1")

    testImplementation("com.squareup.okhttp3:mockwebserver:3.14.0")
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
        applicationId "com.example.moviedb"
        minSdkVersion 19
        targetSdkVersion 28
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        release {
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

    // unit test
    testImplementation 'junit:junit:4.12'
    testImplementation 'org.mockito:mockito-core:2.19.0'
    testImplementation "android.arch.core:core-testing:1.1.1"
}
*/