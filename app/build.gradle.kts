plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.9.20"
}

android {
    namespace = "com.example.profquest2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.profquest2"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc")

    // ktor
    val ktorVersion = "2.3.8"
    implementation("io.ktor:ktor-client-core:$ktorVersion")

    val navVersion = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$navVersion")
    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")
    // Orbit
    val orbitVersion = "6.1.0"
    implementation("org.orbit-mvi:orbit-core:$orbitVersion")
    implementation("org.orbit-mvi:orbit-viewmodel:$orbitVersion")
    implementation("org.orbit-mvi:orbit-compose:$orbitVersion")

    // Gson
    implementation ("com.google.code.gson:gson:2.10.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:hilt-android-compiler:2.48.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("androidx.compose.material:material:1.6.3")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}