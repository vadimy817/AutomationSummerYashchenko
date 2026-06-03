import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    java
    id("custom-analytics")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("archiveBuildLogs") {
    group = "build"
    description = "Фіксує дату та час успішного складання проєкту"
    mustRunAfter(tasks.build)

    doLast {
        val buildDir = layout.buildDirectory.get().asFile
        if (!buildDir.exists()) {
            buildDir.mkdirs()
        }

        val logFile = file("${buildDir}/build-history.txt")
        val currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        logFile.appendText("Проєкт успішно зібрано о: $currentDateTime\n")
        println("==> [Task 3] Лог успішно збережено в build/build-history.txt")
    }
}