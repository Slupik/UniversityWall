/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */
object Versions {
    /*
    ANDROID
     */
    const val lifecycle = "2.1.0"
    const val androidxNavigation = "2.2.1"
    const val androidxAppcompat = "1.1.0"
    const val androidxKtx = "1.2.0"
    const val androidxConstraintLayout = "1.1.3"
    const val androidxFragmentKtx = "1.2.0-rc03"
    const val androidxWork = "2.3.4"
    const val androidxLegacySupport = "1.0.0"
    const val rxandroid = "2.1.1" // https://mvnrepository.com/artifact/io.reactivex.rxjava2/rxandroid
    const val material = "1.1.0"
    const val appcompat7 = "1.1.0"
    const val googleServices = "4.3.3"
    const val firebaseAnalytics = "17.2.2"
    const val firebaseVision = "24.0.2"
    const val firebaseVisionBarcodeModel = "16.0.1"
    const val playVision = "9.4.0+"
    const val room = "2.2.5"

    /*
    PLATFORM AGNOSTIC
     */
    const val gradle = "3.6.1"
    const val gradlePlugin = "1.3.61"
    const val kotlin = "1.3.61"
    const val rxjava = "2.2.19" // https://mvnrepository.com/artifact/io.reactivex.rxjava2/rxjava
    const val rxkotlin = "2.4.0" // https://mvnrepository.com/artifact/io.reactivex.rxjava2/rxkotlin
    const val dagger = "2.25.2" // https://mvnrepository.com/artifact/com.google.dagger/dagger
    const val assistedInject = "0.5.2"
    const val threetenbp = "1.4.0"
    const val retrofit = "2.6.2"

    /*
    TESTING
     */
    const val junit4 = "4.12"
    const val junit5 = "5.5.2"
    const val mockito = "2.2.0"
    const val mockitoToKotlin = "2.2.0"
    const val espresso = "3.2.0"
    const val androidxTestRunner = "1.2.0"
    const val androidxTestExt = "1.1.1"
}

object Deps {
    /*
    PLUGINS, BUILDERS etc.
     */
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val gradlePlugin = "gradle-plugin:${Versions.gradlePlugin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val allOpenPlugin = "org.jetbrains.kotlin:kotlin-allopen:${Versions.kotlin}"
    const val androidxNavigationPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidxNavigation}"

    /*
    ANDROID
     */
    // lifecycle
    const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    // AndroidX
    const val androidxNavigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigation}"
    const val androidxNavigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigation}"
    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.androidxAppcompat}"
    const val androidxKtx = "androidx.core:core-ktx:${Versions.androidxKtx}"
    const val androidxConstraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}"
    const val androidxFragmentKtx = "androidx.fragment:fragment-ktx:${Versions.androidxFragmentKtx}"
    const val androidxLegacySupport = "androidx.legacy:legacy-support-v4:${Versions.androidxLegacySupport}"
    const val androidxWorkRuntime = "androidx.work:work-runtime-ktx:${Versions.androidxWork}"
    const val androidxGcm = "androidx.work:work-gcm:${Versions.androidxWork}" // optional - GCMNetworkManager support
    // Rx
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    const val rxAndroidxWorkRuntime = "androidx.work:work-rxjava2:${Versions.androidxWork}"
    // dagger
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    // material
    const val material = "com.google.android.material:material:${Versions.material}"
    // appcompat
    const val appcompat7 = "com.android.support:appcompat-v7:${Versions.appcompat7}"
    // play services
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics:${Versions.firebaseAnalytics}"
    const val firebaseVision = "com.google.firebase:firebase-ml-vision:${Versions.firebaseVision}"
    const val firebaseVisionBarcodeModel =
        "com.google.firebase:firebase-ml-vision-barcode-model:${Versions.firebaseVisionBarcodeModel}"
    const val playVision = "com.google.android.gms:play-services-vision:${Versions.playVision}"
    // room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomRx = "androidx.room:room-rxjava2:${Versions.room}"

    /*
    PLATFORM AGNOSTIC
     */
    // kotlin
    const val kotlinJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    // rx
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"
    // dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    // assisted inject
    const val assistedInjectAnnotations = "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.assistedInject}"
    const val assistedInjectProcessor = "com.squareup.inject:assisted-inject-processor-dagger2:${Versions.assistedInject}"
    // threetenbp
    const val threetenbp = "org.threeten:threetenbp:${Versions.threetenbp}"
    // retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"

    /*
    TESTING
     */
    const val junit4 = "junit:junit:${Versions.junit4}"
    const val junit5 = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoToKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoToKotlin}"
    const val androidxWorkTesting = "androidx.work:work-testing:${Versions.androidxWork}"
    const val androidxTestRunner = "androidx.test:runner:${Versions.androidxTestRunner}"
    const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxTestExt}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room}"
}