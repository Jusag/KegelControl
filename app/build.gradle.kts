plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.example.kegelcontrol"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kegelcontrol"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.ui:ui:1.5.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:core-ktx:1.4.0")
}
