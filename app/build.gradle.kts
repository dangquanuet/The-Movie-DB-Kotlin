import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    jacoco
}

android {
    namespace = "com.example.moviedb"
    defaultConfig {
        applicationId = "com.example.moviedb"
        buildToolsVersion = "34.0.0"
        minSdk = 23
        compileSdk = 34
        targetSdk = 34
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
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false
            }
        }
        /*create("beta") {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }*/
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    val serverDimension = "server"
    flavorDimensions.addAll(listOf(serverDimension))
    productFlavors {
        create("dev") {
            dimension = serverDimension
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Movie DB Dev")
            buildConfigField("boolean", "MOCK_DATA", "true")
            val keyFile = rootProject.file("signing/debug.properties")
            if (keyFile.exists()) {
                val properties = Properties()
                properties.load(keyFile.inputStream())
                val signKeyName = "dev-key"
                signingConfigs {
                    create(signKeyName) {
                        storeFile = properties["keystore"]?.let { rootProject.file(it) }
                        storePassword = properties["storePassword"]?.toString()
                        keyAlias = properties["keyAlias"]?.toString()
                        keyPassword = properties["keyPassword"]?.toString()
                    }
                }
                signingConfig = signingConfigs.getByName(signKeyName)
            }
        }
        create("prd") {
            dimension = serverDimension
            resValue("string", "app_name", "Movie DB")
            buildConfigField("boolean", "MOCK_DATA", "false")
            val keyFile = rootProject.file("signing/release.properties")
            if (keyFile.exists()) {
                val properties = Properties()
                properties.load(keyFile.inputStream())
                val signKeyName = "prd-key"
                signingConfigs {
                    create(signKeyName) {
                        storeFile = properties["keystore"]?.let { rootProject.file(it) }
                        storePassword = properties["storePassword"]?.toString()
                        keyAlias = properties["keyAlias"]?.toString()
                        keyPassword = properties["keyPassword"]?.toString()
                    }
                }
                signingConfig = signingConfigs.getByName(signKeyName)
            }
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
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
        // https://developer.android.com/topic/libraries/data-binding
        dataBinding = true
        compose = true
    }
    composeOptions {
        // check version here https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    lint {
//        checkReleaseBuilds = false
//        abortOnError = false
    }
}

dependencies {
    // common
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.material:material:1.10.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.20")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")
    implementation("androidx.multidex:multidex:2.0.1")

    // List of KTX extensions
    // https://developer.android.com/kotlin/ktx/extensions-list
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Lifecycle
    // https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")

    // Preferences DataStore
    // https://android-developers.googleblog.com/2020/09/prefer-storing-data-with-jetpack.html
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // room
    // https://developer.android.com/topic/libraries/architecture/room
    implementation("androidx.room:room-runtime:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    // paging
    // https://developer.android.com/topic/libraries/architecture/paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    // navigation
    // https://developer.android.com/jetpack/androidx/releases/navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.5")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

    // coroutines
    // https://github.com/Kotlin/kotlinx.coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    // retrofit
    // https://github.com/square/retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    // OkHttpProfiler
    // https://github.com/itkacher/OkHttpProfiler
    implementation("com.localebro:okhttpprofiler:1.0.8")

    // stetho
    // http://facebook.github.io/stetho/
//    implementation("com.facebook.stetho:stetho:1.5.1")
//    implementation("com.facebook.stetho:stetho-okhttp3:1.5.1")

    // glide
    // https://github.com/bumptech/glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    ksp("com.github.bumptech.glide:ksp:4.16.0")

    // dagger hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:hilt-android-compiler:2.48.1")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")
    ksp("androidx.hilt:hilt-compiler:1.1.0")

    // runtime permission
    // https://github.com/googlesamples/easypermissions
    implementation("pub.devrel:easypermissions:3.0.0")

    // firebase
    // https://firebase.google.com/docs/android/setup
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // lottie
    // https://github.com/airbnb/lottie-android
//    implementation("com.airbnb.android:lottie:3.4.2")

    // timber
    // https://github.com/JakeWharton/timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // unit test
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.6.0")
//    testImplementation("org.mockito:mockito-inline:3.3.3")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.11")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.20")
//    testImplementation("org.robolectric:robolectric:4.3")

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

    // compose
    // https://developer.android.com/jetpack/compose/interop/adding
    // https://developer.android.com/jetpack/compose/setup
//    implementation("androidx.compose.compiler:compiler:1.4.5")
//    implementation("androidx.compose:compose-bom:2023.04.00")
//    androidTestImplementation("androidx.compose:compose-bom:2023.04.00")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.5.4")
    // or Material Design 2
    implementation("androidx.compose.material:material:1.5.4")
    // Material Design 3
    implementation("androidx.compose.material3:material3:1.1.2")
    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
    // Animations
    implementation("androidx.compose.animation:animation:1.5.4")
    // Constraint layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
//    implementation("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    // Optional - Add window size utils
    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")
    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.8.0")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    // Optional - Integration with LiveData
//    implementation("androidx.compose.runtime:runtime-livedata:1.4.3")
    // Lifecycle utilities for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    // navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    // https://github.com/skydoves/landscapist
//    implementation("com.github.skydoves:landscape-bom:2.1.7")
    implementation("com.github.skydoves:landscapist-glide:2.2.10")
    implementation("com.github.skydoves:landscapist-placeholder:2.2.10")
    // https://google.github.io/accompanist/
    // https://github.com/google/accompanist
    val accompanistVersion = "0.32.0"
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-permissions:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-placeholder:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-navigation-material:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-webview:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-adaptive:$accompanistVersion")
}

kapt {
    correctErrorTypes = true
}

jacoco {
    toolVersion = "0.8.8"
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
                sourceName = "${productFlavorName}${
                    buildTypeName.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }
                }"
            }
            val testTaskName = "test${
                sourceName.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }UnitTest"
            // Create coverage task of form 'testFlavorTypeCoverage' depending on 'testFlavorTypeUnitTest'
            task<JacocoReport>("${testTaskName}Coverage") {
                //where store all test to run follow second way above
                group = "coverage"
                description =
                    "Generate Jacoco coverage reports on the ${
                        sourceName.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }
                    } build."
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
                    xml.required.set(true)
                    html.required.set(true)
                }
                dependsOn(testTaskName)
            }
        }
    }
}
