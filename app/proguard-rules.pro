# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ------------------------1.基本不用动区域--------------------------
# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference

-keep class com.peakmain.basiclibrary.permission.PkPermissionFragment {
    *;
}

-keep class com.peakmain.basiclibrary.image.ImageSelectorFragment {
    *;
}

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
# AndroidX
-keep class com.google.android.material.**{*;}
-keep class androidx.**{*;}
-keep public class * extends androidx.**
-keep interface androidx.**{*;}
-keep @androidx.annotation.Keep class *
-keepclassmembers class *{
   @androidx.annotation.Keep *;
}

-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

