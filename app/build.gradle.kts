import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("io.fabric")
    id("com.google.gms.google-services")
    jacoco
}

jacoco {
    toolVersion = "0.8.5"
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
    buildFeatures {
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
    implementation("com.google.android.material:material:1.2.0-alpha06")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinCompilerVersion.VERSION}")
    implementation("androidx.multidex:multidex:2.0.1")

    // List of KTX extensions
    // https://developer.android.com/kotlin/ktx/extensions-list
    implementation("androidx.core:core-ktx:1.3.0-rc01")
//    implementation("androidx.activity:activity-ktx:1.1.0")
    implementation("androidx.fragment:fragment-ktx:1.2.4")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")

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
    implementation("androidx.navigation:navigation-runtime-ktx:2.3.0-alpha06")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0-alpha06")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0-alpha06")
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
    implementation("com.google.firebase:firebase-analytics:17.4.0")
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