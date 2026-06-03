import java.io.File

// TASK 1: Підрахунок файлів
val countSourceFiles by tasks.registering {
    group = "analytics"
    description = "Рахує кількість Java-файлів у проєкті"

    doLast {
        val srcDir = File(project.projectDir, "src/main/java")
        val javaFilesCount = if (srcDir.exists()) {
            srcDir.walkTopDown().filter { it.isFile && it.extension == "java" }.count()
        } else {
            0
        }
        project.extensions.extraProperties["javaFilesCount"] = javaFilesCount
        println("==> [Task 1] Знайдено Java-файлів: $javaFilesCount")
    }
}

// TASK 2: Виведення звіту
val printProjectInfo by tasks.registering {
    group = "analytics"
    description = "Виводить фінальний звіт про проєкт"
    dependsOn(countSourceFiles)

    doLast {
        val name = project.name
        val version = project.version
        val filesCount = if (project.extensions.extraProperties.has("javaFilesCount")) {
            project.extensions.extraProperties["javaFilesCount"]
        } else {
            "0"
        }

        println("   Звіт автоматизації   ")
        println(" Назва проєкту: $name")
        println(" Поточна версія: $version")
        println(" Кількість Java-файлів у src: $filesCount")
    }
}