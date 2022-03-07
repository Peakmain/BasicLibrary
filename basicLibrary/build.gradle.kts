plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}
android {
    compileSdkVersion(AndroidConfigConstants.compileSdkVersion)

    defaultConfig {
        minSdkVersion(AndroidConfigConstants.minSdkVersion)
        targetSdkVersion(AndroidConfigConstants.targetSdkVersion)
        versionCode = AndroidConfigConstants.versionCode
        versionName = AndroidConfigConstants.versionName

    }

    buildTypes {
        getByName("debug") {

        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

    }
    buildFeatures {
        dataBinding = true
    }
    packagingOptions {
        exclude("AndroidManifest.xml")
    }
    //依赖操作
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(DependenciesConfigConstants.kotlin_stdlib)
    implementation(DependenciesConfigConstants.core_ktx) {
        isTransitive = false
    }
    implementation(DependenciesConfigConstants.appcompat)
    api(DependenciesConfigConstants.kotlin_coroutines_core) {
        isTransitive = false
    }
    api(DependenciesConfigConstants.kotlin_coroutines_android) {
        isTransitive = false
    }

    //OkHttp3
    api(DependenciesConfigConstants.okhttp3_logging_interceptor)
    //Retrofit网络请求
    api(DependenciesConfigConstants.retrofit_gson) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
    }
    api(DependenciesConfigConstants.retrofit_rxjava) {
        exclude(group = "com.squareup.retrofit2", module = "retrofit")
    }
    api(DependenciesConfigConstants.retrofit_adapter) {
        isTransitive = false
    }
    api(DependenciesConfigConstants.rxandroid)
    api(DependenciesConfigConstants.mmkv)
    implementation(DependenciesConfigConstants.cardview)

    kapt(DependenciesConfigConstants.databinding)
    api(DependenciesConfigConstants.basicUI)
    api(DependenciesConfigConstants.recyclerview)
    api(DependenciesConfigConstants.material)

}
