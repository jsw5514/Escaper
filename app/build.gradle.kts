plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.swjeon.escaper"
    compileSdk = 35

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.swjeon.escaper"
        minSdk = 26
        targetSdk = 35
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
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.0")
    implementation(project(":a2dg"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}