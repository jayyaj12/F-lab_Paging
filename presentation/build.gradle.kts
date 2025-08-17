plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlin.compose)
}

android {

    namespace = "com.aos.myapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.aos.myapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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

    buildFeatures {
        dataBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    // Room
    implementation(libs.bundles.room)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    kapt(libs.androidx.room.compiler)

    // ViewPager2
    implementation(libs.androidx.viewpager2)

    // Paging
    implementation(libs.bundles.paging)

    // Glide
    implementation(libs.glide)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Timber
    implementation(libs.timber)

    // Network (Retrofit + Serialization + OkHttp)
    implementation(libs.bundles.network)

    // AndroidX core set and UI components
    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}