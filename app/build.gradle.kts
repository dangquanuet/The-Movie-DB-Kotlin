import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("com.google.firebase.firebase-perf")
    jacoco
}

android {
    defaultConfig {
        applicationId = "com.example.moviedb"
        buildToolsVersion("30.0.2")
        minSdkVersion(17)
        compileSdkVersion(30)
        targetSdkVersion(30)
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
        versionCode = 1
        versionName = "1.0.0"
        setProperty(
            "archivesBaseName",
            "MovieDB_${SimpleDateFormat("yyyyMMdd-HHmm").format(Date())}_v${versionName}(${versionCode})"
        )
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val debugFile = rootProject.file("signing/debug.properties")
    val releaseFile = rootProject.file("signing/release.properties")
    if (releaseFile.exists() && debugFile.exists()) {
        val releaseProperties = Properties()
        releaseProperties.load(FileInputStream(releaseFile))
        val debugProperties = Properties()
        debugProperties.load(FileInputStream(debugFile))
        signingConfigs {
            create("debug-key") {
                storeFile = debugProperties["keystore"]?.let { rootProject.file(it) }
                storePassword = debugProperties["storePassword"]?.toString()
                keyAlias = debugProperties["keyAlias"]?.toString()
                keyPassword = debugProperties["keyPassword"]?.toString()
            }
            create("release-key") {
                storeFile = releaseProperties["keystore"]?.let { rootProject.file(it) }
                storePassword = releaseProperties["storePassword"]?.toString()
                keyAlias = releaseProperties["keyAlias"]?.toString()
                keyPassword = releaseProperties["keyPassword"]?.toString()
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            firebaseCrashlytics {
                mappingFileUploadEnabled = false
            }
            signingConfig = signingConfigs.getByName("debug-key")
        }
        create("staging") {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            firebaseCrashlytics {
                mappingFileUploadEnabled = true
            }
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug-key")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            firebaseCrashlytics {
                mappingFileUploadEnabled = true
            }
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release-key")
        }
    }

    flavorDimensions("default")

    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Movie DB Dev")
            buildConfigField("boolean", "MOCK_DATA", "false")
        }
        create("mock") {
            applicationIdSuffix = ".mock"
            resValue("string", "app_name", "Movie DB Mock")
            buildConfigField("boolean", "MOCK_DATA", "true")
        }
        create("prd") {
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

        when (flavorName) {
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
    buildFeatures {
        dataBinding = true
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    // common
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0-alpha1")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("com.google.android.material:material:1.3.0-alpha04")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.10")
    implementation("androidx.multidex:multidex:2.0.1")

    // List of KTX extensions
    // https://developer.android.com/kotlin/ktx/extensions-list
    implementation("androidx.core:core-ktx:1.5.0-alpha05")
    implementation("androidx.activity:activity-ktx:1.2.0-beta02")
    implementation("androidx.fragment:fragment-ktx:1.3.0-beta02")

    // Lifecycle
    // https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")

    // Preferences DataStore
    // https://android-developers.googleblog.com/2020/09/prefer-storing-data-with-jetpack.html
    implementation("androidx.datastore:datastore-preferences:1.0.0-alpha05")

    // room
    // https://developer.android.com/topic/libraries/architecture/room
    implementation("androidx.room:room-runtime:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")
    implementation("androidx.room:room-ktx:2.2.5")

    // paging
    // https://developer.android.com/topic/libraries/architecture/paging
    implementation("androidx.paging:paging-runtime-ktx:3.0.0-alpha10")

    // navigation
    // https://developer.android.com/jetpack/androidx/releases/navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.3.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.2")

    // coroutines
    // https://github.com/Kotlin/kotlinx.coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9")

    // moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.11.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.11.0")

    // retrofit
    // https://github.com/square/retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")

    // stetho
    // http://facebook.github.io/stetho/
    implementation("com.facebook.stetho:stetho:1.5.1")
    implementation("com.facebook.stetho:stetho-okhttp3:1.5.1")

    // glide
    // https://github.com/bumptech/glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.11.0")

    //dagger hilt
    implementation("com.google.dagger:hilt-android:2.28-alpha")
    kapt("com.google.dagger:hilt-android-compiler:2.28-alpha")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02")
    kapt("androidx.hilt:hilt-compiler:1.0.0-alpha02")

    // runtime permission
    // https://github.com/googlesamples/easypermissions
    implementation("pub.devrel:easypermissions:3.0.0")

    // firebase
    // https://firebase.google.com/docs/android/setup
    implementation("com.google.firebase:firebase-analytics:18.0.0")
    implementation("com.google.firebase:firebase-crashlytics:17.3.0")
    implementation("com.google.firebase:firebase-perf-ktx:19.0.10")

    // lottie
    // https://github.com/airbnb/lottie-android
    implementation("com.airbnb.android:lottie:3.4.0")

    // timber
    // https://github.com/JakeWharton/timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // unit test
    testImplementation("junit:junit:4.13")
    testImplementation("org.mockito:mockito-core:3.6.0")
    testImplementation("android.arch.core:core-testing:1.1.1")

    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    androidTestImplementation("android.arch.persistence.room:testing:1.1.1")
    androidTestImplementation("android.arch.core:core-testing:1.1.1")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.4.0")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.20")


    /**
     * for buildSrc
     */
/*
    // common
    implementation(Libs.appcompat)
    implementation(Libs.legacySupport)
    implementation(Libs.constraintLayout)
    implementation(Libs.recyclerview)
    implementation(Libs.material)
    implementation(Libs.stdLib)
    implementation(Libs.multidex)

    // List of KTX extensions
    // https://developer.android.com/kotlin/ktx/extensions-list
    implementation(Libs.coreKtx)
    implementation(Libs.activityKtx)
    implementation(Libs.fragmentKtx)

    // Lifecycle
    // https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation(Libs.lifecycleViewModelKtx)
    implementation(Libs.lifecycleLiveDataKtx)
    implementation(Libs.lifecycleKtx)
    implementation(Libs.lifecycleJava8)

    // room
    // https://developer.android.com/topic/libraries/architecture/room
    implementation(Libs.roomRuntime)
    kapt(Libs.roomCompiler)
    implementation(Libs.roomKtx)

    // paging
    // https://developer.android.com/topic/libraries/architecture/paging
    implementation(Libs.paging)

    // navigation
    // https://developer.android.com/jetpack/androidx/releases/navigation
    implementation(Libs.navigationRuntimeKtx)
    implementation(Libs.navigationFragmentKtx)
    implementation(Libs.navigationUiKtx)
//    implementation(Libs.navigationDynamicModule)

    // work
    // https://developer.android.com/topic/libraries/architecture/workmanager
//    implementation(Libs.workManager)

    // rx
    // https://github.com/ReactiveX/RxJava
//    implementation(Libs.rxjava)

    // coroutines
    // https://github.com/Kotlin/kotlinx.coroutines
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)
    testImplementation(Libs.coroutinesTest)

    // moshi
    implementation(Libs.moshi)
    kapt(Libs.moshiCodeGen)

    // retrofit
    // https://github.com/square/retrofit
    implementation(Libs.retrofit)
    implementation(Libs.retrofitMoshi)
    implementation(Libs.okLogging)
//    implementation(Libs.retrofitRxjava)

    // stetho
    // http://facebook.github.io/stetho/
    implementation(Libs.stetho)
    implementation(Libs.stethoOkhttp3)

    // glide
    // https://github.com/bumptech/glide
    implementation(Libs.glideRuntime)
    kapt(Libs.glideCompiler)

    //dagger hilt
    implementation(Libs.daggerHiltAndroid)
    kapt(Libs.daggerHiltAndroidCompiler)
    implementation(Libs.daggerHiltViewModel)
    kapt(Libs.daggerHiltViewModelCompiler)

    // runtime permission
    // https://github.com/googlesamples/easypermissions
//    implementation(Libs.easyPermissions)

    // firebase
    // https://firebase.google.com/docs/android/setup
    implementation(Libs.firebaseAnalytics)
    implementation(Libs.firebaseCrashlytics)
    implementation(Libs.firebasePerformance)

    // lottie
    // https://github.com/airbnb/lottie-android
//    implementation(Libs.lottie)

    // timber
    // https://github.com/JakeWharton/timber
    implementation(Libs.timber)

    implementation(Libs.viewpager2)

    compileOnly(Libs.lombok)
    annotationProcessor(Libs.annotationLombok)

    // unit test
    testImplementation(Libs.junit)
    testImplementation(Libs.mockitoCore)
    androidTestImplementation(Libs.mockitoAndroid)
    testImplementation(Libs.testCore)
    testImplementation(Libs.archCore)
    */
}

jacoco {
    toolVersion = "0.8.6"
}

/** There are two ways to see test result:
 * FIRST
 * To run this test coverage with buildTypes: Debug; Flavor: Dev
 * use command: ./gradlew clean testDevDebugUnitTestCoverage
 *
 * See result at:
 * app/build/reports/jacoco/testDevDebugUnitTestCoverage/html/index.html
 *
 * SECOND:
 *  - Click Gradle on the right menu of Android Studio IDE
 *  - At Project name, expand "app", expand "Tasks", expand "coverage"
 *  - Run any test you want
 */
project.afterEvaluate {
    // Grab all build types and product flavors
    val buildTypeNames: List<String> = android.buildTypes.map { it.name }
    val productFlavorNames: ArrayList<String> = ArrayList(android.productFlavors.map { it.name })
    // When no product flavors defined, use empty
    if (productFlavorNames.isEmpty()) productFlavorNames.add("")
    productFlavorNames.forEach { productFlavorName ->
        buildTypeNames.forEach { buildTypeName ->
            val sourceName: String
            val sourcePath: String
            if (productFlavorName.isEmpty()) {
                sourcePath = buildTypeName
                sourceName = buildTypeName
            } else {
                sourcePath = "${productFlavorName}/${buildTypeName}"
                sourceName = "${productFlavorName}${buildTypeName.capitalize()}"
            }
            val testTaskName = "test${sourceName.capitalize()}UnitTest"
            // Create coverage task of form 'testFlavorTypeCoverage' depending on 'testFlavorTypeUnitTest'
            task<JacocoReport>("${testTaskName}Coverage") {
                //where store all test to run follow second way above
                group = "coverage"
                description =
                    "Generate Jacoco coverage reports on the ${sourceName.capitalize()} build."
                val excludeFiles = arrayListOf(
                    "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
                    "**/*Test*.*", "android/**/*.*",
                    "**/*_MembersInjector.class",
                    "**/Dagger*Component.class",
                    "**/Dagger*Component\$Builder.class",
                    "**/*_*Factory.class",
                    "**/*ComponentImpl.class",
                    "**/*SubComponentBuilder.class",
                    "**/*Creator.class",
                    "**/*Application*.*",
                    "**/*Activity*.*",
                    "**/*Fragment*.*",
                    "**/*Adapter*.*",
                    "**/*Dialog*.*",
                    "**/*Args*.*",
                    "**/*Companion*.*",
                    "**/*Kt*.*",
                    "**/com/example/moviedb/di/**/*.*",
                    "**/com/example/moviedb/ui/navigation/**/*.*",
                    "**/com/example/moviedb/ui/widgets/**/*.*"
                )

                //Explain to Jacoco where are you .class file java and kotlin
                classDirectories.setFrom(
                    fileTree("${project.buildDir}/intermediates/classes/${sourcePath}").exclude(
                        excludeFiles
                    ),
                    fileTree("${project.buildDir}/tmp/kotlin-classes/${sourceName}").exclude(
                        excludeFiles
                    )
                )
                val coverageSourceDirs = arrayListOf(
                    "src/main/java",
                    "src/$productFlavorName/java",
                    "src/$buildTypeName/java"
                )

                additionalSourceDirs.setFrom(files(coverageSourceDirs))

                //Explain to Jacoco where is your source code
                sourceDirectories.setFrom(files(coverageSourceDirs))

                //execute file .exec to generate data report
                executionData.setFrom(files("${project.buildDir}/jacoco/${testTaskName}.exec"))

                reports {
                    xml.isEnabled = true
                    html.isEnabled = true
                }
                dependsOn(testTaskName)
            }
        }
    }
}
