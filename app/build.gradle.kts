plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.daggerpractice"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.daggerpractice"
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // Dagger
    val dagger_version = "2.50"

    // Dagger2 core
    implementation ("com.google.dagger:dagger:$dagger_version")
    annotationProcessor ("com.google.dagger:dagger-compiler:$dagger_version")

    // Dagger Android
    implementation ("com.google.dagger:dagger-android:$dagger_version")
    implementation ("com.google.dagger:dagger-android-support:$dagger_version")
    annotationProcessor ("com.google.dagger:dagger-android-processor:$dagger_version")

    // Glide
    val glideVersion = "4.16.0"
    implementation ("com.github.bumptech.glide:glide:$glideVersion")
    annotationProcessor ("com.github.bumptech.glide:compiler:$glideVersion")

    val lifecycle_version = "2.7.0"
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    val retrofit_version = "2.9.0"
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")

    // RxJava
    val rxjava_version = "3.1.5"
    implementation ("io.reactivex.rxjava3:rxjava:$rxjava_version")

//    // Rx-Retrofit Call Adapter
//    val rxcalladapter_version = "2.5.0"
//    implementation ("com.squareup.retrofit2:adapter-rxjava2:$rxcalladapter_version") // Retrofit call adapter

    val retroadapterrx_version = "2.9.0"
    implementation ("com.squareup.retrofit2:adapter-rxjava3:$retroadapterrx_version")

    // Reactive Streams (convert Observable to LiveData)
    implementation ("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version")

    val nav_version = "2.7.7"
    implementation ("androidx.navigation:navigation-fragment:$nav_version")
    implementation ("androidx.navigation:navigation-ui:$nav_version")
    implementation ("androidx.navigation:navigation-runtime:$nav_version")
}