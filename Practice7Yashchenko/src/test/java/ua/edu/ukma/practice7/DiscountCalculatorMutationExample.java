package ua.edu.ukma.practice7;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Example for PIT report")
class DiscountCalculatorMutationExample {

    @Test
    void badMutationTest() {
        assertThat(1200)
                .isGreaterThan(0);
    }

    @Test
    void fixedMutationTest() {
        assertThat(
                new DiscountCalculator()
                        .calculateDiscount(1200)
        ).isEqualTo(20);
    }
}
