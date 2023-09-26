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
    implementation("androidx.compose.ui:ui:1.5.1")
    implementation("androidx.compose.ui:ui-tooling:1.5.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.1")
    implementation("androidx.compose.foundation:foundation:1.5.1")
    implementation("androidx.compose.material:material:1.5.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.3")
    implementation("androidx.navigation:navigation-compose:2.7.3")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.camera:camera-core:1.2.3")
    implementation("androidx.camera:camera-camera2:1.2.3")
    implementation("androidx.camera:camera-lifecycle:1.2.3")
    implementation("androidx.camera:camera-video:1.2.3")
    implementation("androidx.camera:camera-view:1.2.3")
    implementation("androidx.camera:camera-extensions:1.2.3")
    implementation("com.google.zxing:core:3.3.0")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.google.code.gson:gson:2.8.8")
}
