plugins {
    kotlin("jvm")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = Deps.Version.jvmTarget
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = Deps.Version.jvmTarget
    }
}

dependencies {
    implementation(project(":knot"))
    implementation(Deps.detektApi)
    implementation(kotlin(Deps.kotlinJdk))

    testImplementation(Deps.junit)
    testImplementation(Deps.truth)
    testImplementation(Deps.detektTest)
}
