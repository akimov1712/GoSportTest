plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.ksp)
    alias(libs.plugins.navSafeArgs) apply false
    alias(libs.plugins.hilt) apply false
}