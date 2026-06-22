package ua.edu.ukma.practice7;

import java.util.List;

public class OrderRepository {

    public List<String> getOrders() {
        return List.of(
                "Laptop",
                "Phone",
                "Tablet"
        );
    }
}
