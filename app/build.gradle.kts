import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
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
        kotlinCompilerExtensionVersion = "1.5.1"
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
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")

    //youtubePlayer
    implementation(libs.core)

    //coil-image
    implementation(libs.coil.compose)

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //OKHTTP
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //live data
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")

    //Coil
    //implementation(libs.accompanist.coil)
    implementation("io.coil-kt:coil-compose:2.6.0")

    //lifecycle
    //liveCycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    implementation("androidx.activity:activity-ktx:1.9.0")

    //Document
    implementation(libs.androidx.documentfile)

    //Datastore
    implementation(libs.androidx.datastore.preferences)

    // MockWebServer
    testImplementation(libs.mockwebserver)

    // JUnit
    testImplementation(libs.junit)

    //accompanist pager
    implementation("com.google.accompanist:accompanist-pager:0.28.0")


    // Coroutine Test
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.dotenv.kotlin)

}