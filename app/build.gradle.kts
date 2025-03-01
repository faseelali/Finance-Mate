plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin") // Add this if missing
}

android {
    namespace = "com.example.financeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.financeapp"
        minSdk = 24
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures{
        viewBinding = true
    }

}

dependencies {

    // Kotlin
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Room dependencies
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    // Room
    implementation (libs.androidx.room.runtime)

    // LiveData and ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)

    // Hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)

    // Navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    // MPAndroidChart
    implementation (libs.mpandroidchart)

    // Coroutines
    implementation (libs.kotlinx.coroutines.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}