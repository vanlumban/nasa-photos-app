import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

val localPropertiesFile = rootProject.file("local.properties")
val nasaApiKey: String = if (localPropertiesFile.exists()) {
    localPropertiesFile.reader().use { reader ->
        reader.readLines()
            .firstOrNull { it.startsWith("NASA_API_KEY=") }
            ?.split("=")?.get(1)
            ?: ""
    }
} else {
    ""
}

android {
    namespace = "com.vanlumban.nasaphotos"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.vanlumban.nasaphotos"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Force expose the NASA API key to the app code so the users can run the app
        buildConfigField("String", "NASA_API_KEY", "f4OyIH2wfW4ogrdNJfQlu4gNvDM6kbgkMsdNodcz")
//        buildConfigField("String", "NASA_API_KEY", "\"$nasaApiKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // AndroidX + Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    // Json Serialization
    implementation(libs.kotlinx.serialization.json)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)

    // Coil (image loading)
    implementation(libs.coil.compose)

    // Koin DI
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Swipe to Refresh + Placeholder (shimmer)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.placeholder)

    // Unit Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)

    // Android Instrumentation Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Compose Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
