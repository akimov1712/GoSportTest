import ru.topbun.buildsrc.MetaData

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navSafeArgs)
    alias(libs.plugins.hilt)
}

android {
    namespace = MetaData.applicationId
    compileSdk = MetaData.compileSdkVersion

    defaultConfig {
        applicationId = MetaData.applicationId
        minSdk = MetaData.minSdkVersion
        targetSdk = MetaData.targetSdkVersion
        versionCode = MetaData.versionCode
        versionName = MetaData.versionName

        testInstrumentationRunner = MetaData.testInstrumentationRunner
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
    buildFeatures{
        viewBinding = true
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {


//    RemoteSource
    implementation (libs.retrofit)
    implementation (libs.retrofitGsonConverter)
    implementation(libs.okHttp)
    implementation(libs.okHttp.interceptor)

//    JSON
    implementation (libs.gson)

//    Hilt
    implementation (libs.hiltAndroid)
    ksp (libs.hiltCompiler)

//    View
    implementation (libs.glide)

//    Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)

//    Room
    implementation(libs.roomRuntime)
    ksp(libs.roomCompiler)
    implementation(libs.roomKtx)

//    ViewModel
    implementation (libs.viewModelLifecycle)

//    Default
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}