plugins {
    kotlin("jvm") version "1.7.21"
    java
}

val projectJvmTarget = "17"

tasks.compileJava {
    targetCompatibility = projectJvmTarget
    sourceCompatibility = projectJvmTarget
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xextended-compiler-checks",
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlin.time.ExperimentalTime",
            "-opt-in=kotlin.ExperimentalStdlibApi",
            "-Xjvm-default=all-compatibility",
        )
        jvmTarget = projectJvmTarget
    }
}


repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("me.friwi:jogl-all:v2.4.0-rc-20210111")
    implementation("me.friwi:gluegen-rt:v2.4.0-rc-20210111")

    listOf(
        "javafx-base",
        "javafx-graphics",
        "javafx-controls",
    ).forEach { module ->
        listOf("win", "linux", "mac").forEach { platform ->
            implementation(group= "org.openjfx", name= module, version= "17", classifier = platform)
        }
    }

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks.test {
    useJUnitPlatform()
}

