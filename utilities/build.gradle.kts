plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.detekt)
    `maven-publish`
}

group = "org.connecttag"
version = "1.0.1"

android {
    namespace = libs.versions.app.version.appId.get()

    compileSdk = libs.versions.app.build.compileSDKVersion.get().toInt()

    defaultConfig {
        minSdk = libs.versions.app.build.minimumSDK.get().toInt()
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            consumerProguardFiles("proguard-rules.pro")
        }
    }

 



    compileOptions {
        val currentJavaVersionFromLibs = JavaVersion.valueOf(libs.versions.app.build.javaVersion.get())
        sourceCompatibility = currentJavaVersionFromLibs
        targetCompatibility = currentJavaVersionFromLibs
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = project.libs.versions.app.build.kotlinJVMTarget.get()
        kotlinOptions.freeCompilerArgs = listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xcontext-receivers"
        )
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = false
        abortOnError = true
        warningsAsErrors = true
        baseline = file("lint-baseline.xml")
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }
}

publishing.publications {
    create<MavenPublication>("release") {
        description = "A shared library containing common data models for projects."

        afterEvaluate {
            from(components["release"])
        }
    }
}

detekt {
    baseline = file("detekt-baseline.xml")
}

dependencies {
    implementation(libs.androidx.annotation.jvm)
    implementation(libs.gson)
    // For Phone Numbers
    implementation(libs.libphonenumber)
}

