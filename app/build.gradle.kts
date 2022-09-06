import org.jetbrains.kotlin.konan.properties.Properties
import java.io.File
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")
}

android {
    compileSdk = AndroidProjectConfig.compileSdk

    defaultConfig {
        applicationId = AndroidProjectConfig.applicationId
        minSdk = AndroidProjectConfig.minSdk
        targetSdk = AndroidProjectConfig.targetSdk
        versionCode = AndroidProjectConfig.versionCode
        versionName = AndroidProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    val prop = Properties().apply {
        load(FileInputStream(File(rootProject.rootDir, "key.properties")))
    }


    flavorDimensions += listOf("default")
    productFlavors {
        create("production") {
            dimension = "default"
            buildConfigField("String", "BASE_URL", "\"${prop.getProperty("BASE_URL")}\"")
            buildConfigField("String", "API_KEY", "\"${prop.getProperty("API_KEY")}\"")
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true // add this
    }

}

dependencies {
    //common android libs
    implementation(Libraries.activityKtx)
    implementation(Libraries.androidxCoreKtx)
    implementation(Libraries.androidxAppcompat)
    implementation(Libraries.googleAndroidMaterial)
    implementation(Libraries.androidxConstraintLayout)

    //network client & chucker
    implementation(Libraries.retrofit2)
    implementation(Libraries.retrofitConverterGson)
    implementation(Libraries.chucker)
    releaseImplementation(Libraries.chuckerNoOp)

    //coroutines
    implementation(Libraries.coroutineCore)
    implementation(Libraries.coroutineAndroid)
    testImplementation(Libraries.coroutineTest)

    //livedata and viewmodel
    implementation(Libraries.lifecycleViewModelKtx)
    implementation(Libraries.lifecycleLiveDataKtx)
    implementation(Libraries.lifecycleRuntimeKtx)

    //jetpack paging
    implementation(Libraries.paging)

    //coil
    implementation(Libraries.coil)

    //koin
    implementation(Libraries.koinAndroid)

    //room
    implementation(Libraries.room)
    implementation(Libraries.roomKtx)
    kapt(Libraries.roomKapt)

    //testing libraries
    testImplementation(kotlin("test"))
    testImplementation(Libraries.junit)
    testImplementation(Libraries.mockk)
    testImplementation(Libraries.coreTesting)
    implementation(Libraries.espressoIdlingResource)
    androidTestImplementation(Libraries.espressoCore)
    androidTestImplementation(Libraries.espressoContrib)
    androidTestImplementation(Libraries.androidJunit)
    androidTestImplementation(Libraries.androidTestRunner)
    androidTestImplementation(Libraries.androidTestRules)
    androidTestImplementation(Libraries.androidxTestCore)
    androidTestImplementation(Libraries.androidxTestCoreKtx)

    //time utils library
    implementation(Libraries.timeAgo)
}
