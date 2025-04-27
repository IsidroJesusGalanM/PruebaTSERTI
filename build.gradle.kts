// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.kotlinAndroidKsp) apply false
    alias(libs.plugins.realm.gradle) apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.22" apply false
}

buildscript{
    dependencies {

        classpath(libs.gradle)
        classpath(libs.realm.gradle.plugin.v10151)
    }
}

