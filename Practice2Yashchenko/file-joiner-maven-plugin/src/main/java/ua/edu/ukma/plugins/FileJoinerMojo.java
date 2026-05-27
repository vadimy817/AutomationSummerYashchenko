package ua.edu.ukma.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Mojo(name = "join", defaultPhase = LifecyclePhase.PACKAGE)
public class FileJoinerMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.sourceDirectory}", readonly = true, required = true)
    private File sourceDirectory;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true, required = true)
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        File outputFile = new File(outputDirectory, "all-sources-combined.txt");
        getLog().info("ПЛАГІН 1. Початок об'єднання Java-файлів у: " + outputFile.getAbsolutePath());

        if (!sourceDirectory.exists()) {
            getLog().warn("Папка з вихідним кодом не знайдена: " + sourceDirectory.getAbsolutePath());
            return;
        }

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            try (Stream<Path> paths = Files.walk(sourceDirectory.toPath())) {
                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".java"))
                        .forEach(path -> {
                            try {
                                writer.write("Файл проєкту студента: " + sourceDirectory.toPath().relativize(path) + "\n");

                                String content = Files.readString(path);
                                writer.write(content);
                                writer.write("\n\n");

                                getLog().info("Успішно додано файл: " + path.getFileName());
                            } catch (IOException e) {
                                getLog().error("Не вдалося прочитати файл: " + path, e);
                            }
                        });
            }
            getLog().info("ПЛАГІН 1. Усі файли успішно об'єднано!");
        } catch (IOException e) {
            throw new MojoExecutionException("Помилка під час запису фінального файлу", e);
        }
    }
}