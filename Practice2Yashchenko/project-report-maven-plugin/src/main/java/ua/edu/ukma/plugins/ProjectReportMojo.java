package ua.edu.ukma.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class ProjectReportMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.sourceDirectory}", readonly = true, required = true)
    private File sourceDirectory;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true, required = true)
    private File outputDirectory;

    @Parameter(defaultValue = "${project.artifactId}", readonly = true, required = true)
    private String artifactId;

    @Override
    public void execute() throws MojoExecutionException {
        File reportFile = new File(outputDirectory, "project-summary-report.txt");
        getLog().info("ПЛАГІН 2. Генеруємо звіт про роботу в: " + reportFile.getAbsolutePath());

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        AtomicInteger totalFiles = new AtomicInteger(0);
        AtomicLong totalLines = new AtomicLong(0);

        if (sourceDirectory.exists()) {
            try (Stream<Path> paths = Files.walk(sourceDirectory.toPath())) {
                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".java"))
                        .forEach(path -> {
                            totalFiles.incrementAndGet();
                            try {
                                long lines = Files.lines(path).count();
                                totalLines.addAndGet(lines);
                            } catch (IOException e) {
                            }
                        });
            } catch (IOException e) {
                throw new MojoExecutionException("Помилка підрахунку метрик", e);
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(reportFile))) {
            writer.println("Звіт системи автоматизації проєкту");
            writer.println("Сканований модуль: " + artifactId);
            writer.println("Кількість вихідних Java-файлів: " + totalFiles.get());
            writer.println("Загальна кількість написаних рядків коду: " + totalLines.get());
            writer.println("Версія Java середовища розробника: " + System.getProperty("java.version"));
            writer.println("Операційна система комп'ютера: " + System.getProperty("os.name"));

            getLog().info("ПЛАГІН 2. Звіт успішно згенеровано! Студент написав " + totalLines.get() + " рядків коду.");
        } catch (IOException e) {
            throw new MojoExecutionException("Не вдалося створити звіт", e);
        }
    }
}
