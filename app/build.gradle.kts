import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("io.fabric")
    id("com.google.gms.google-services")
}

android {

    defaultConfig {
        applicationId = "com.example.moviedb"
        minSdkVersion(17)
        compileSdkVersion(29)
        targetSdkVersion(29)
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
//            isUseProguard = false
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
//            isUseProguard = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

    // https://developer.android.com/topic/libraries/data-binding
    buildFeatures{
        dataBinding = true
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    // common
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta4")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("com.google.android.material:material:1.2.0-alpha05")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinCompilerVersion.VERSION}")
    implementation("androidx.multidex:multidex:2.0.1")

    // List of KTX extensions
    // https://developer.android.com/kotlin/ktx/extensions-list
    implementation("androidx.core:core-ktx:1.3.0-beta01")
//    implementation("androidx.activity:activity-ktx:1.1.0")
    implementation("androidx.fragment:fragment-ktx:1.2.4")

    // Lifecycle
    // https://developer.android.com/jetpack/androidx/releases/lifecycle
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    // alternately - if using Java8, use the following instead of lifecycle-compiler, provide @OnLifecycleEvent
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    // Saved state module for ViewModel
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0")

    // room
    // https://developer.android.com/topic/libraries/architecture/room
    implementation("androidx.room:room-runtime:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.2.5")

    // paging
    // https://developer.android.com/topic/libraries/architecture/paging
    implementation("androidx.paging:paging-runtime-ktx:2.1.2")

    // navigation
    // https://developer.android.com/jetpack/androidx/releases/navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.3.0-alpha04")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0-alpha04")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0-alpha04")
    // Dynamic Feature Module Support
//    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.3.0-alpha02")

    // work
    // https://developer.android.com/topic/libraries/architecture/workmanager
//    implementation("androidx.work:work-runtime-ktx:2.3.2")

    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // rx
    // https://github.com/ReactiveX/RxJava
//    implementation("io.reactivex.rxjava3:rxjava:3.0.0")

    // coroutines
    // https://github.com/Kotlin/kotlinx.coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.5")

    // gson
    implementation("com.google.code.gson:gson:2.8.6")

    // retrofit
    // https://github.com/square/retrofit
    implementation("com.squareup.retrofit2:retrofit:2.7.2")
    implementation("com.squareup.retrofit2:converter-gson:2.7.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.4.0")
//    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.0")

    // glide
    // https://github.com/bumptech/glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.11.0")

    // koin
    // https://github.com/InsertKoinIO/koin
//    implementation("org.koin:koin-core:2.0.1")
//    implementation("org.koin:koin-android:2.0.1")
    implementation("org.koin:koin-androidx-viewmodel:2.0.1")

    // lottie
    // https://github.com/airbnb/lottie-android
    implementation("com.airbnb.android:lottie:3.4.0")

    // runtime permission
//    implementation("pub.devrel:easypermissions:3.0.0")

    // firebase
    implementation("com.google.firebase:firebase-analytics:17.3.0")
    implementation("com.crashlytics.sdk.android:crashlytics:2.10.1")

    // unit test
    testImplementation("junit:junit:4.13")
    testImplementation("org.mockito:mockito-core:3.3.3")
    testImplementation("android.arch.core:core-testing:1.1.1")

    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    androidTestImplementation("android.arch.persistence.room:testing:1.1.1")
    androidTestImplementation("android.arch.core:core-testing:1.1.1")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.4.0")
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