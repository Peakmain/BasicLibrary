plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}
android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${KotlinConstants.kotlin_version}")
    implementation("androidx.core:core-ktx:1.3.2") {
        isTransitive = false
    }
    implementation("androidx.appcompat:appcompat:1.2.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1") {
        isTransitive = false
    }
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.0") {
        isTransitive = false
    }

    //OkHttp3
    api("com.squareup.okhttp3:logging-interceptor:3.11.0")
    //Retrofit网络请求
    api("com.squareup.retrofit2:converter-gson:2.6.2") {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
    }
    api("com.squareup.retrofit2:adapter-rxjava2:2.4.0") {
        exclude(group = "com.squareup.retrofit2", module = "retrofit")
    }
    api("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2") {
        isTransitive = false
    }
    api("io.reactivex.rxjava2:rxandroid:2.1.1")
    api("com.tencent:mmkv:1.2.11")
    implementation("androidx.cardview:cardview:1.0.0")

    kapt("com.android.databinding:compiler:3.1.4")
    api("com.github.Peakmain:BasicUI:1.1.12")
    api("androidx.recyclerview:recyclerview:1.2.0")
    api("com.google.android.material:material:1.3.0")

}
