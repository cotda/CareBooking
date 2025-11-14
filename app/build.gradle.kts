import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {

    namespace = "com.example.carebooking"
    compileSdk = 36

    // Load API_BASE_URL từ file api.properties
    val apiProps = Properties()
    apiProps.load(project.file("api.properties").inputStream())

    val apiUrl: String = apiProps.getProperty("API_BASE_URL")

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.carebooking"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // Đưa vào BuildConfig.java
        buildConfigField("String", "API_BASE_URL", "\"$apiUrl\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")
}
