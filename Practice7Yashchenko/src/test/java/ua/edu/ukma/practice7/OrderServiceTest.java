package ua.edu.ukma.practice7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    InventoryService inventoryService;

    @Mock
    PaymentGateway paymentGateway;

    @Mock
    NotificationService notificationService;

    @InjectMocks
    OrderService orderService;

    @Test
    void shouldProcessOrderSuccessfully() {

        Order order =
                new Order("Laptop", 1, 1000);

        when(inventoryService.isAvailable("Laptop", 1))
                .thenReturn(true);

        when(paymentGateway.charge(1000))
                .thenReturn(true);

        boolean result =
                orderService.processOrder(order);

        assertThat(result).isTrue();
    }

    @Test
    void shouldFailWhenItemNotAvailable() {

        Order order =
                new Order("Laptop", 1, 1000);

        when(inventoryService.isAvailable("Laptop", 1))
                .thenReturn(false);

        boolean result =
                orderService.processOrder(order);

        assertThat(result).isFalse();
    }

    @Test
    void shouldFailWhenPaymentFails() {

        Order order =
                new Order("Laptop", 1, 1000);

        when(inventoryService.isAvailable("Laptop", 1))
                .thenReturn(true);

        when(paymentGateway.charge(1000))
                .thenReturn(false);

        boolean result =
                orderService.processOrder(order);

        assertThat(result).isFalse();
    }

    @Test
    void shouldVerifyNotificationCall() {

        Order order =
                new Order("Laptop", 1, 1000);

        when(inventoryService.isAvailable("Laptop", 1))
                .thenReturn(true);

        when(paymentGateway.charge(1000))
                .thenReturn(true);

        orderService.processOrder(order);

        verify(notificationService)
                .sendConfirmation("Order processed successfully");
    }

    @Test
    void shouldCallNotificationExactlyOnce() {

        Order order =
                new Order("Laptop", 1, 1000);

        when(inventoryService.isAvailable("Laptop", 1))
                .thenReturn(true);

        when(paymentGateway.charge(1000))
                .thenReturn(true);

        orderService.processOrder(order);

        verify(notificationService, times(1))
                .sendConfirmation(anyString());
    }

    @Test
    void shouldNeverSendNotificationWhenPaymentFails() {

        Order order =
                new Order("Laptop", 1, 1000);

        when(inventoryService.isAvailable("Laptop", 1))
                .thenReturn(true);

        when(paymentGateway.charge(1000))
                .thenReturn(false);

        orderService.processOrder(order);

        verify(notificationService, never())
                .sendConfirmation(anyString());
    }
}
