plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        targetSdk = 35
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":network"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
}
