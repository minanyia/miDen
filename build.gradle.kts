// Top-level build file
buildscript {
    extra.apply {
        set("room_version", "2.6.1")
    }
}

plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false
}