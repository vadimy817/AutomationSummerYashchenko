package ua.edu.ukma.practice7;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountCalculatorTest {

    private final DiscountCalculator calculator =
            new DiscountCalculator();

    @Test
    void shouldReturn20PercentDiscount() {

        assertThat(
                calculator.calculateDiscount(1200)
        ).isEqualTo(20);
    }

    @Test
    void shouldReturn10PercentDiscount() {

        assertThat(
                calculator.calculateDiscount(700)
        ).isEqualTo(10);
    }

    @Test
    void shouldReturnZeroDiscount() {

        assertThat(
                calculator.calculateDiscount(300)
        ).isEqualTo(0);
    }

    @Test
    void softAssertionsExample() {

        int highDiscount =
                calculator.calculateDiscount(1500);

        int mediumDiscount =
                calculator.calculateDiscount(700);

        int lowDiscount =
                calculator.calculateDiscount(200);

        SoftAssertions softly =
                new SoftAssertions();

        softly.assertThat(highDiscount)
                .isEqualTo(20);

        softly.assertThat(mediumDiscount)
                .isEqualTo(10);

        softly.assertThat(lowDiscount)
                .isEqualTo(0);

        softly.assertAll();
    }
}
