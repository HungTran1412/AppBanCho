plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
}

android {
    namespace = "dev.mhung.ltmobile.petapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.mhung.ltmobile.petapplication"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Nếu không dùng Proguard, có thể xóa proguardFiles
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }



    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0") // Cập nhật mới nhất
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // Cập nhật mới nhất
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation(libs.play.services.maps)
    implementation(libs.activity) // Cập nhật mới nhất

    // Unit Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Retrofit (Dùng để gọi API)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
