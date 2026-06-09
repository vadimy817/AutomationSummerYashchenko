package ua.edu.ukma.json.processor;

import ua.edu.ukma.json.annotations.GenerateDto;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("ua.edu.ukma.json.annotations.GenerateDto")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class JsonDtoProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) return false;

        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateDto.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement classElement = (TypeElement) element;
                generateDtoClass(classElement);
            }
        }
        return true;
    }

    private void generateDtoClass(TypeElement classElement) {
        String packageName = processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();
        String originalClassName = classElement.getSimpleName().toString();
        GenerateDto annotation = classElement.getAnnotation(GenerateDto.class);
        String targetClassName = originalClassName + annotation.classNameSuffix();

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + targetClassName);
            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
                out.println("package " + packageName + ";");
                out.println();
                out.println("import ua.edu.ukma.json.annotations.JsonField;");
                out.println();
                out.println("public class " + targetClassName + " {");

                for (Element enclosed : classElement.getEnclosedElements()) {
                    if (enclosed.getKind() == ElementKind.FIELD) {
                        String fieldName = enclosed.getSimpleName().toString();
                        String fieldType = enclosed.asType().toString();

                        out.println("    @JsonField(\"" + fieldName.toLowerCase() + "_\")");
                        out.println("    private " + fieldType + " " + fieldName + ";");
                        out.println();
                    }
                }

                out.println("    public " + targetClassName + "() {}");
                out.println();

                out.print("    public " + targetClassName + "(");
                boolean first = true;
                for (Element enclosed : classElement.getEnclosedElements()) {
                    if (enclosed.getKind() == ElementKind.FIELD) {
                        if (!first) out.print(", ");
                        out.print(enclosed.asType().toString() + " " + enclosed.getSimpleName());
                        first = false;
                    }
                }
                out.println(") {");
                for (Element enclosed : classElement.getEnclosedElements()) {
                    if (enclosed.getKind() == ElementKind.FIELD) {
                        String name = enclosed.getSimpleName().toString();
                        out.println("        this." + name + " = " + name + ";");
                    }
                }
                out.println("    }");
                out.println("}");
            }
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, e.getMessage());
        }
    }
}