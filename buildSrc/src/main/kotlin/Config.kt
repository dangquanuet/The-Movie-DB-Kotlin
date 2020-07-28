object Android {
    const val minSdk = 21
    const val targetSdk = 29
    const val applicationId = ""

    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Modules {
}

object Libs {

    // Support libs
    const val kotlinVersion = "1.3.72"
    const val appcompat = "androidx.appcompat:appcompat:1.1.0"
    const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta8"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
    const val material = "com.google.android.material:material:1.3.0-alpha02"
    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val multidex = "androidx.multidex:multidex:2.0.1"

    // List of KTX extensions
    // https://developer.android.com/kotlin/ktx/extensions-list
    const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha01"
    const val activityKtx = "androidx.activity:activity-ktx:1.1.0"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.5"

    // lifecycle
    // https://developer.android.com/jetpack/androidx/releases/lifecycle
    private const val lifecycleVersion = "2.2.0"
    const val lifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    // alternately - if using Java8, use the following instead of lifecycle-compiler, provide @OnLifecycleEvent
    const val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
    // Saved state module for ViewModel
    const val lifecycleSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion"

    // room
    // https://developer.android.com/topic/libraries/architecture/room
    private const val roomVersion = "2.2.5"
    const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
    // Kotlin Extensions and Coroutines support for Room
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"

    // paging
    // https://developer.android.com/topic/libraries/architecture/paging
    const val paging = "androidx.paging:paging-runtime-ktx:2.1.2"

    // navigation
    // https://developer.android.com/jetpack/androidx/releases/navigation
    const val navigationVersion = "2.3.0"
    const val navigationRuntimeKtx = "androidx.navigation:navigation-runtime-ktx:$navigationVersion"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:$navigationVersion"

    // Dynamic Feature Module Support
    const val navigationDynamicModule =
        "androidx.navigation:navigation-dynamic-features-fragment::$navigationVersion"

    // work
    // https://developer.android.com/topic/libraries/architecture/workmanager
    const val workManager = "androidx.work:work-runtime-ktx:2.3.4"

    // rx
    // https://github.com/ReactiveX/RxJava
    const val rxjava = "io.reactivex.rxjava3:rxjava:3.0.0"

    // coroutines
    // https://github.com/Kotlin/kotlinx.coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.5"

    // gson
    const val gson = "com.google.code.gson:gson:2.8.6"

    // retrofit
    // https://github.com/square/retrofit
    private const val retrofitVersion = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    const val retrofitRxjava = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    const val okLogging = "com.squareup.okhttp3:logging-interceptor:4.7.2"

    // stetho
    // http://facebook.github.io/stetho/
    private const val stethoVersion = "1.5.1"
    const val stetho = "com.facebook.stetho:stetho:$stethoVersion"
    const val stethoOkhttp3 = "com.facebook.stetho:stetho-okhttp3:$stethoVersion"

    // glide
    // https://github.com/bumptech/glide
    private const val glideVersion = "4.11.0"
    const val glideRuntime = "com.github.bumptech.glide:glide:$glideVersion"
    const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"

    // runtime permission
    // https://github.com/googlesamples/easypermissions
    const val easyPermissions = "pub.devrel:easypermissions:3.0.0"

    // Timber for logging
    // https://github.com/JakeWharton/timber
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    // lottie
    // https://github.com/airbnb/lottie-android
    const val lottie = "com.airbnb.android:lottie:3.4.0"

    // koin
    // https://github.com/InsertKoinIO/koin
    const val koinVersion = "2.1.5"
    const val koinCore = "org.koin:koin-core:$koinVersion"
    const val koinAndroid = "org.koin:koin-android:$koinVersion"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:$koinVersion"
    const val koinScope = "org.koin:koin-androidx-scope:$koinVersion"

    // dagger 2
    // https://developer.android.com/training/dependency-injection/dagger-basics
//    const val daggerCore = "com.google.dagger:dagger:${Versions.dagger}"
//    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
//    const val daggerSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
//
//    const val daggerProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
//    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    // dagger hilt
    // https://developer.android.com/training/dependency-injection/hilt-android
    const val daggerHiltVersion = "2.28-alpha"
    const val daggerHiltAndroid = "com.google.dagger:hilt-android:$daggerHiltVersion"
    const val daggerHiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$daggerHiltVersion"
    const val daggerHiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01"
    const val daggerHiltViewModelCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha01"

    // firebase
    // https://firebase.google.com/docs/android/setup
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:17.4.4"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:17.1.1"

    // eventBus
    const val eventBus = "org.greenrobot:eventbus:3.2.0"

    // leak canary
    // https://square.github.io/leakcanary/
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.2"

    const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"

    // test
    const val junit = "junit:junit:4.13"
    const val mockitoCore = "org.mockito:mockito-core:3.3.3"
    const val archCore = "android.arch.core:core-testing:1.1.1"

    const val testRunner = "com.android.support.test:runner:1.0.2"
    const val espressoCore = "com.android.support.test.espresso:espresso-core:3.0.2"
    const val roomTest = "android.arch.persistence.room:testing:1.1.1"

    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.4.0"
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
}
