plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.nafis.agrosewa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nafis.agrosewa"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
        dataBinding=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.google.mlkit:vision-common:17.3.0")
    implementation("com.google.mlkit:image-labeling-common:18.1.0")
    implementation("com.google.mlkit:image-labeling-default-common:17.0.0")
    implementation("androidx.core:core-i18n:1.0.0-alpha01")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //text dimension
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")


    //lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.8.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.0")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Material Design
    implementation ("com.google.android.material:material:1.12.0")

    implementation ("de.hdodenhof:circleimageview:3.1.0")






    // The following line is optional, as the core library is included indirectly by camera-camera2
    implementation ("androidx.camera:camera-core:1.3.3")
    implementation ("androidx.camera:camera-camera2:1.3.3")
    // If you want to additionally use the CameraX Lifecycle library
    implementation ("androidx.camera:camera-lifecycle:$1.3.3")
    // If you want to additionally use the CameraX VideoCapture library
    implementation ("androidx.camera:camera-video:1.3.3")
    // If you want to additionally use the CameraX View class
    implementation ("androidx.camera:camera-view:1.3.3")
    // If you want to additionally add CameraX ML Kit Vision Integration
//    implementation ("androidx.camera:camera-ml-kit-vision:1.3.3")
    // If you want to additionally use the CameraX Extensions library
    implementation ("androidx.camera:camera-extensions:1.3.3")
    implementation ("com.google.guava:guava:30.1.1-android")

    // lotti animation

    implementation ("com.airbnb.android:lottie:3.4.0")

    // dialogplus
    implementation ("com.orhanobut:dialogplus:1.11@aar")






}