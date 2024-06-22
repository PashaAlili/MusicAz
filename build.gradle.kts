buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath(libs.gradle)
        classpath(libs.firebase.crashlytics.gradle)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("kapt") version "1.9.10"

    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("com.android.application") version "8.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("androidx.navigation.safeargs") version "2.7.4" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
}