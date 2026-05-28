plugins {

    id("com.android.application")

    id("org.jetbrains.kotlin.android")

    id("org.jetbrains.kotlin.plugin.compose")

    id("com.google.devtools.ksp")

    id("com.google.dagger.hilt.android")
}

android {

    namespace = "com.avis.skycast"

    compileSdk = 35

    defaultConfig {

        applicationId = "com.avis.skycast"

        minSdk = 24
        targetSdk = 35

        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
    }

    buildFeatures {

        compose = true
    }
}

kotlin {

    jvmToolchain(11)
}

dependencies {

    implementation(
        platform("androidx.compose:compose-bom:2024.06.00")
    )

    implementation(
        "androidx.core:core-ktx:1.13.1"
    )

    implementation(
        "androidx.activity:activity-compose:1.9.1"
    )

    implementation(
        "androidx.lifecycle:lifecycle-runtime-ktx:2.8.4"
    )

    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4"
    )

    implementation(
        "androidx.lifecycle:lifecycle-runtime-compose:2.8.4"
    )

    implementation(
        "androidx.compose.ui:ui"
    )

    implementation(
        "androidx.compose.ui:ui-graphics"
    )

    implementation(
        "androidx.compose.ui:ui-tooling-preview"
    )

    implementation(
        "androidx.compose.material3:material3"
    )

    implementation(
        "androidx.navigation:navigation-compose:2.8.0"
    )

    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1"
    )

    implementation(
        "androidx.room:room-runtime:2.6.1"
    )
    implementation( "androidx.datastore:datastore-preferences:1.1.1" )
    implementation(
        "androidx.room:room-ktx:2.6.1"
    )

    ksp(
        "androidx.room:room-compiler:2.6.1"
    )

    implementation(
        "com.squareup.retrofit2:retrofit:2.11.0"
    )

    implementation(
        "com.squareup.retrofit2:converter-gson:2.11.0"
    )

    implementation(
        "com.google.dagger:hilt-android:2.52"
    )

    ksp(
        "com.google.dagger:hilt-compiler:2.52"
    )

    implementation(
        "androidx.hilt:hilt-navigation-compose:1.2.0"
    )

    implementation(
        "com.google.android.gms:play-services-location:21.3.0"
    )

    testImplementation(
        "junit:junit:4.13.2"
    )

    androidTestImplementation(
        platform("androidx.compose:compose-bom:2024.06.00")
    )

    androidTestImplementation(
        "androidx.test.ext:junit:1.2.1"
    )

    androidTestImplementation(
        "androidx.test.espresso:espresso-core:3.6.1"
    )

    androidTestImplementation(
        "androidx.compose.ui:ui-test-junit4"
    )

    debugImplementation(
        "androidx.compose.ui:ui-tooling"
    )

    debugImplementation(
        "androidx.compose.ui:ui-test-manifest"
    )
}
