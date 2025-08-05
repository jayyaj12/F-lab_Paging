plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
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

    // paging
    implementation("androidx.paging:paging-runtime:3.3.6")

    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    kapt("com.google.dagger:hilt-compiler:2.56.2")

    // timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")

    implementation("androidx.activity:activity-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}