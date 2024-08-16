buildscript {
    repositories {
        google()
        mavenCentral()
        // jcenter() // jcenter is being deprecated, consider replacing with mavenCentral
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath ("com.android.tools.build:gradle:7.0.4") // Use the latest stable version
    }
}



// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}