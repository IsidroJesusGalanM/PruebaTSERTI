plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinAndroidKsp)
    id ("kotlin-kapt")
    id("realm-android")

}
android {
    namespace = "com.example.pruebatecnicaserti"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pruebatecnicaserti"
        minSdk = 29
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
    kotlinOptions {
        jvmTarget = "11"
    }

    viewBinding {
        enable = true
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    //lottie
    implementation(libs.lottie)
    implementation(libs.androidx.navigation.fragment.ktx)
    //realm
    ksp(libs.hilt.compiler)

    kapt(libs.realm.kapt)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)


    //dagger hilt
    implementation(libs.hilt.android)


    //glide (carga de imagenes)
    implementation(libs.glide)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}