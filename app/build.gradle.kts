plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.shot)
}

android {
    namespace = Config.applicationId
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner
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
        jvmTarget = Config.javaVersion.majorVersion
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.kotlin.compiler.get()
    }

    buildFeatures {
        compose = true
    }

    lint {
        lintConfig = file(Config.lintFile)
        checkDependencies = true
        warningsAsErrors = true
    }
}

shot {
    applicationId = Config.applicationId
}

dependencies {
    modules(DependenciesGroups.modules)

    // Android libs:
    implementation(libs.bundles.androidx.core)

    // Dagger:
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Compose:
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose)

    // UI Tests:
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.ui.tests)
}
