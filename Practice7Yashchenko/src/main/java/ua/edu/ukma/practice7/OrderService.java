package ua.edu.ukma.practice7;

public class OrderService {

    private final InventoryService inventoryService;
    private final PaymentGateway paymentGateway;
    private final NotificationService notificationService;

    public OrderService(
            InventoryService inventoryService,
            PaymentGateway paymentGateway,
            NotificationService notificationService
    ) {
        this.inventoryService = inventoryService;
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
    }

    public boolean processOrder(Order order) {

        if (!inventoryService.isAvailable(
                order.getItem(),
                order.getQuantity())) {
            return false;
        }

        if (!paymentGateway.charge(order.getAmount())) {
            return false;
        }

        notificationService.sendConfirmation(
                "Order processed successfully");

        return true;
    }
}
