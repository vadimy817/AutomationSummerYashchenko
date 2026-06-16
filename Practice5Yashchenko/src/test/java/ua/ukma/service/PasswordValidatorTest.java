package ua.ukma.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Тестування PasswordValidator")
class PasswordValidatorTest {

    private final PasswordValidator validator = new PasswordValidator();

    // 1) Простий тест + assumption + тег
    @Test
    @Tag("fast")
    @DisplayName("Простий тест: Перевірка валідної довжини стандартного паролю")
    void testValidPasswordLength() {
        String javaVersion = System.getProperty("java.version");
        assumeTrue(javaVersion.startsWith("17") || javaVersion.startsWith("21") || javaVersion.startsWith("25"),
                "Тест скасовано: Поточна версія Java нижче ніж Java 17, що не підходить для JUnit 6");

        boolean result = validator.isValidLength("NaUKMA_2026");
        assertTrue(result, "Пароль 'NaUKMA_2026' повинен мати валідну довжину (від 8 до 25)");
    }

    // 2) Параметризований тест (1 статичний параметр)
    @ParameterizedTest
    @ValueSource(strings = {"SuperSecret2026", "ValidPassword", "ExactlyEightChars"})
    @Tag("fast")
    @DisplayName("Параметризований тест (1 параметр): Різні валідні паролі")
    void testValidLengthsWithSingleParameter(String password) {
        assertTrue(validator.isValidLength(password),
                "Пароль '" + password + "' має бути визнаний валідним за довжиною");
    }

    // 3) Параметризований тест (набір статичних параметрів)
    @ParameterizedTest
    @CsvSource({
            "NoSpecials, 0",
            "Pass!@#, 3",
            "Admin_2026_$, 3",
            "(#MyPassword=), 4"
    })
    @Tag("slow")
    @DisplayName("Параметризований тест (набір CSV): Підрахунок спецсимволів")
    void testCountSpecialCharacters(String inputPassword, int expectedCount) {
        int actualCount = validator.countSpecialCharacters(inputPassword);
        assertEquals(expectedCount, actualCount,
                "Кількість спецсимволів у '" + inputPassword + "' не збігається з очікуваною");
    }

    // 4) Динамічний тест (@TestFactory)
    @TestFactory
    @Tag("slow")
    @DisplayName("Динамічні тести: Перевірка граничних значень (Edge Cases)")
    Collection<DynamicTest> dynamicTestsForEdgeCases() {
        return List.of(
                DynamicTest.dynamicTest("Перевірка для значення null",
                        () -> assertFalse(validator.isValidLength(null))),
                DynamicTest.dynamicTest("Перевірка для занадто короткого паролю (менше 8)",
                        () -> assertFalse(validator.isValidLength("Short1"))),
                DynamicTest.dynamicTest("Перевірка для занадто довгого паролю (більше 25)",
                        () -> assertFalse(validator.isValidLength("ThisIsAnIncrediblyLongPasswordForTestingPurposes2026")))
        );
    }
}
