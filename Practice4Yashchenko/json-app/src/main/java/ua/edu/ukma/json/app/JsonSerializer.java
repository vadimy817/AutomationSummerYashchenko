package ua.edu.ukma.json.app;

import ua.edu.ukma.json.annotations.JsonField;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonSerializer {

    public static String serialize(Object obj) {
        try {
            Class<?> clazz = obj.getClass();
            Map<String, String> jsonElements = new HashMap<>();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                if (field.isAnnotationPresent(JsonField.class)) {
                    JsonField annotation = field.getAnnotation(JsonField.class);
                    String jsonKey = annotation.value().isEmpty() ? field.getName() : annotation.value();
                    Object value = field.get(obj);

                    jsonElements.put(jsonKey, value != null ? value.toString() : "null");
                }
            }

            String result = jsonElements.entrySet().stream()
                    .map(entry -> "\"" + entry.getKey() + "\": \"" + entry.getValue() + "\"")
                    .collect(Collectors.joining(", "));

            return "{" + result + "}";
        } catch (Exception e) {
            throw new RuntimeException("Помилка серіалізації", e);
        }
    }
}