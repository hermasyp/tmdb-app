/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

object AndroidProjectConfig {
    const val minSdk = 21
    const val compileSdk = 32
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0"
    const val applicationId = "com.catnip.goplaytmdb"
}

object Libraries {

    object Versions {
        const val coreKtx = "1.8.0"
        const val appcompat = "1.4.2"
        const val activityKtx = "1.5.1"
        const val constraintLayout = "2.1.4"
        const val lifecycle = "2.5.0"
        const val googleMaterial = "1.6.1"
        const val gson = "2.9.0"
        const val coroutine = "1.6.1"
        const val retrofit = "2.9.0"
        const val chucker = "3.5.2"
        const val junit = "4.13.2"
        const val androidTestJunit = "1.1.3"
        const val espresso = "3.4.0"
        const val coil = "2.1.0"
        const val mockk = "1.12.7"
        const val jetpackPaging = "3.1.1"
        const val timeAgo = "4.0.3"
        const val coreTesting = "2.1.0"
        const val androidxTextCore = "1.4.0"
        const val androidTestRunner = "1.1.0"
        const val androidTestRules = "1.1.0"
        const val koin = "3.2.0"
        const val room = "2.4.3"
        const val shimmer = "0.5.0"
        const val splashscreenApi = "1.0.0"

    }
    //android library
    const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidxConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val googleAndroidMaterial =
        "com.google.android.material:material:${Versions.googleMaterial}"
    const val splashscreenApi =
        "androidx.core:core-splashscreen:${Versions.splashscreenApi}"

    //coroutine
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"


    // network and image loader
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    const val chuckerNoOp = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"

    //ui libs
    const val paging = "androidx.paging:paging-runtime:${Versions.jetpackPaging}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"

    //coil
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"

    //room
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomKapt = "androidx.room:room-compiler:${Versions.room}"


    //testing libraries
    const val junit = "junit:junit:${Versions.junit}"
    const val androidxTestCore = "androidx.test:core:${Versions.androidxTextCore}"
    const val androidxTestCoreKtx = "androidx.test:core-ktx:${Versions.androidxTextCore}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    const val androidJunit = "androidx.test.ext:junit:${Versions.androidTestJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoIdlingResource = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val androidTestRules = "androidx.test:rules:${Versions.androidTestRules}"
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"


}