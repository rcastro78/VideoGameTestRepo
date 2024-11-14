plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "sv.com.videogametest"
    compileSdk = 35

    defaultConfig {
        applicationId = "sv.com.videogametest"
        minSdk = 28
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)

    testImplementation ("org.mockito:mockito-core:5.2.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("com.google.truth:truth:1.1.5")
    implementation ("io.insert-koin:koin-android:3.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("androidx.compose.material:material:1.7.4")
    implementation ("com.squareup.retrofit2:converter-scalars:2.1.0")
    implementation ("com.squareup.retrofit2:retrofit:2.8.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.8.1")
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")

    testImplementation ("junit:junit:4.13.2")
    testImplementation ("io.mockk:mockk:1.13.4")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    testImplementation ("io.insert-koin:koin-test:3.4.0")
    testImplementation ("io.mockk:mockk:1.13.4")
    // Implementation of the AndroidX SQLite interfaces via the Android framework APIs.
    implementation("androidx.sqlite:sqlite-framework:2.4.0")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation(libs.coil.compose)
}