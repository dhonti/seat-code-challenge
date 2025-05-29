plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = Dependencies.Modules.commonsUi.toNamespace()
    compileSdk = Config.targetSdk

    defaultConfig {
        minSdk = Config.minSdk
        consumerProguardFiles(Config.consumerRules)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(Config.proguardFile),
                Config.proguardRules
            )
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.kotlin.compiler.get()
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Android libs:
    implementation(libs.bundles.androidx.core)

    // Hilt:
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Compose:
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
}