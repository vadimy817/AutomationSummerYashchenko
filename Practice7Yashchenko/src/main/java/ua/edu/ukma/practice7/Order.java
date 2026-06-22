package ua.edu.ukma.practice7;

public class Order {

    private final String item;
    private final int quantity;
    private final double amount;

    public Order(String item, int quantity, double amount) {
        this.item = item;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }
}
