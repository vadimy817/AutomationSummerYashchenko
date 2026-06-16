import org.gradle.internal.impldep.org.junit.platform.engine.discovery.ClassNameFilter.excludeClassNamePatterns

plugins {
    java
}

group = "ua.ukma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.0")
    testImplementation("org.junit.platform:junit-platform-suite:1.11.0")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.register<Test>("testFast") {
    group = "verification"
    useJUnitPlatform {
        includeTags("fast")
        excludeClassNamePatterns(".*Suite")
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.register<Test>("testSlow") {
    group = "verification"
    useJUnitPlatform {
        includeTags("slow")
        excludeClassNamePatterns(".*Suite")
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
}