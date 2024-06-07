import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
}

android {
    namespace = "com.lidm.facillify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lidm.facillify"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"https://lucky.widzzz.com/\"")
        buildConfigField("String", "CHATBOT_URL","\"https://api.openai.com/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "OPENAI_SECRET_KEY", "\"${properties.getProperty("OPENAI_SECRET_KEY")}\"")
        buildConfigField("String", "OPENAI_ORGANIZATION", "\"${properties.getProperty("OPENAI_ORGANIZATION")}\"")
        buildConfigField("String", "OPENAI_PROJECT", "\"${properties.getProperty("OPENAI_PROJECT")}\"")
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.ui.text.google.fonts)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)

    //nav
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    
    //Material icons
    implementation(libs.androidx.material.icons.extended)

    //exo-player
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.media3.exoplayer.dash)
    implementation(libs.media3.ui)

    //youtubePlayer
    implementation(libs.core)

    //coil-image
    implementation(libs.coil.compose)

    //Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)

    //OKHTTP
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    //live data
    implementation(libs.androidx.runtime.livedata)

    //Coil
    //implementation(libs.accompanist.coil)
    implementation(libs.coil.compose)

    //lifecycle
    //liveCycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.activity.ktx)

    //Document
    implementation(libs.androidx.documentfile)

    //Datastore
    implementation(libs.androidx.datastore.preferences)

    // MockWebServer
    testImplementation(libs.mockwebserver)

    // JUnit
    testImplementation(libs.junit)

    //accompanist pager
    implementation(libs.accompanist.pager)


    // Coroutine Test
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.dotenv.kotlin)

    //COIL
    implementation(libs.coil.kt.coil)

    //SWIPE REFRESH
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.swiperefresh.v0263beta)
    implementation(libs.google.accompanist.flowlayout)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}