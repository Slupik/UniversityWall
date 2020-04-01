/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Created by Sebastian Witasik on 30.03.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
private typealias AndroidBaseExtension = BaseExtension

open class BaseAndroidPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins()
        project.configureAndroid()
        project.configureDependencies()
    }
}

private fun Project.configurePlugins() {
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-android-extensions")
    plugins.apply("kotlin-kapt")
}

private fun Project.configureAndroid() = this.extensions.getByType<AndroidBaseExtension>().run {

    compileSdkVersion(AppConfig.compileSdk)

    defaultConfig {
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

internal fun Project.configureDependencies() {
    val project = this
    dependencies.apply {
        implementation(project.fileTree(mapOf(
            "dir" to "libs",
            "include" to listOf("*.jar")
        )))

        implementation(project.project(":Model"))

        implementation(Deps.kotlinJdk8)

        implementation(Deps.rxjava)
        implementation(Deps.rxkotlin)
        implementation(Deps.rxandroid)

        api(Deps.dagger)
        api(Deps.daggerAndroid)
        api(Deps.daggerAndroidSupport)
        annotationProcessor(Deps.daggerAndroidProcessor)
        annotationProcessor(Deps.daggerCompiler)
        kapt(Deps.daggerCompiler)

        implementation(Deps.androidxAppcompat)

        testImplementation(Deps.junit4)
        androidTestImplementation(Deps.espresso)
    }
}