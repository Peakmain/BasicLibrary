// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${KotlinConstants.gradle_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${KotlinConstants.kotlin_version}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { setUrl("https://jitpack.io") }
    }
}
tasks{
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
