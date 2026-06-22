package ua.edu.ukma.practice7;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryTest {

    private final OrderRepository repository =
            new OrderRepository();

    @Test
    void shouldContainLaptop() {

        assertThat(repository.getOrders())
                .contains("Laptop");
    }

    @Test
    void shouldHaveSizeThree() {

        assertThat(repository.getOrders())
                .hasSize(3);
    }

    @Test
    void shouldContainExactlyElements() {

        assertThat(repository.getOrders())
                .containsExactly(
                        "Laptop",
                        "Phone",
                        "Tablet"
                );
    }
}
