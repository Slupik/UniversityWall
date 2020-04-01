import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.file.ConfigurableFileTree

/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

/**
 * Created by Sebastian Witasik on 30.03.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
internal fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

internal fun DependencyHandler.implementation(project: Project) {
    add("implementation", project)
}

internal fun DependencyHandler.implementation(fileTree: ConfigurableFileTree) {
    add("implementation", fileTree)
}

internal fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

internal fun DependencyHandler.compileOnly(depName: String) {
    add("compileOnly", depName)
}

internal fun DependencyHandler.api(depName: String) {
    add("api", depName)
}

internal fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}

internal fun DependencyHandler.androidTestImplementation(depName: String) {
    add("androidTestImplementation", depName)
}

internal fun DependencyHandler.annotationProcessor(depName: String) {
    add("annotationProcessor", depName)
}