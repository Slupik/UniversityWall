/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

plugins {
    `kotlin-dsl`
    `java`
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        register("baseAndroidPlugin") {
            id = "base-android-plugin"
            implementationClass = "BaseAndroidPlugin"
        }
    }
}

repositories {
    jcenter()
    google()
}

dependencies {
    compileOnly(gradleApi())

    implementation("com.android.tools.build:gradle:3.6.1")
    implementation(kotlin("gradle-plugin", "1.3.50"))
    implementation(kotlin("android-extensions"))
}