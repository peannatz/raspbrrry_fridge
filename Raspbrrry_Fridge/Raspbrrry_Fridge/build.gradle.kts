plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.raspbrrry_fridge.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.raspbrrry_fridge.android"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
        viewBinding = true

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.5.2")
    implementation("androidx.compose.ui:ui-tooling:1.5.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.2")
    implementation("androidx.compose.foundation:foundation:1.5.2")
    implementation("androidx.compose.material:material:1.5.2")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.0-alpha06")

    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.3")
    implementation("androidx.navigation:navigation-compose:2.7.3")

    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.chargemap.compose:numberpicker:1.0.3")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.alexstyl.swipeablecard:swipeablecard:0.1.0")
}
