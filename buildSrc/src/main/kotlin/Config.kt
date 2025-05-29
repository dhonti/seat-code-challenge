import org.gradle.api.JavaVersion

object Config {
    const val applicationId = "com.dhontiveros.seatcodechallenge"
    const val versionCode = 1
    const val versionName = "1.0"
    const val compileSdk = 35
    const val minSdk = 26
    const val targetSdk = 35

    const val testInstrumentationRunner = "com.dhontiveros.seatcodechallenge.HiltTestRunner"
    const val proguardFile = "proguard-android-optimize.txt"
    const val proguardRules = "proguard-rules.pro"
    const val consumerRules = "consumer-rules.pro"

    const val lintFile = "lint.xml"

    val javaVersion = JavaVersion.VERSION_17
    const val jvmTarget = "17"
}