package ua.edu.ukma.practice7;

public class DiscountCalculator {

    public int calculateDiscount(int amount) {

        if (amount > 1000) {
            return 20;
        }
        else if (amount > 500) {
            return 10;
        }

        return 0;
    }
}