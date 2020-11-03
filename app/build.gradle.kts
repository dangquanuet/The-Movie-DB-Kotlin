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
//    implementation(Libs.activityKtx)
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
    testImplementation(Libs.archCore)
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