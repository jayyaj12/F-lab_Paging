import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream()) // 루트 기준 경로
}

android {
    namespace = "com.aos.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "KAKAO_API_KEY",  "\"${properties["KAKAO_API_KEY"]}\"")
        buildConfigField("String", "KAKAO_SEARCH_VIDEO_URL", "\"${properties["KAKAO_SEARCH_VIDEO_URL"]}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":domain"))

    // Room
    implementation(libs.bundles.room)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)

    // paging
    implementation(libs.bundles.paging)
    
    // timber
    implementation(libs.timber)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // network (retrofit + serialization + okhttp)
    implementation(libs.bundles.network)

    implementation(libs.bundles.androidx.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}