plugins {
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.detekt).apply(false)

}

tasks.register<Delete>("clean") {
    delete {
        layout.buildDirectory.asFile
    }
}
