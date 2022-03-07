/**
 * author ：Peakmain
 * createTime：2022/3/6
 * mail:2726449200@qq.com
 * describe：
 */
object KotlinConstants {
    const val gradle_version = "4.0.1"
    const val kotlin_version = "1.3.72"
}

object AndroidConfigConstants {
    const val compileSdkVersion = 30
    const val minSdkVersion = 16
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0"

}

object DependenciesConfigConstants {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${KotlinConstants.kotlin_version}"
    const val core_ktx = "androidx.core:core-ktx:1.3.2"
    const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    const val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1"
    const val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.0"
    const val okhttp3_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:3.11.0"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:2.6.2"
    const val retrofit_rxjava = "com.squareup.retrofit2:adapter-rxjava2:2.4.0"
    const val retrofit_adapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val mmkv = "com.tencent:mmkv:1.2.11"
    const val cardview = "androidx.cardview:cardview:1.0.0"
    const val databinding = "com.android.databinding:compiler:3.1.4"
    const val basicUI = "com.github.Peakmain:BasicUI:1.1.12"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.0"
    const val material = "com.google.android.material:material:1.3.0"


}
