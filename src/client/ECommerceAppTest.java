package client;

import facade.OrderProcessingFacade;

public class ECommerceAppTest {

    public static void main(String[] args) {
        System.out.println("Running Facade Pattern Tests");
        System.out.println("===============================\n");

        testSuccessfulOrder();
        testFailedOrderInventory();
        testFailedOrderPayment();

        System.out.println("\nAll tests completed!");
    }

    static void testSuccessfulOrder() {
        System.out.println("Test 1: Successful Order Processing");
        System.out.println("-".repeat(40));

        OrderProcessingFacade facade = new OrderProcessingFacade();
        var request = new OrderProcessingFacade.OrderRequest(
                "PROD123", 2, "4111111111111111", "test@example.com"
        );

        var result = facade.placeOrder(request);

        if (result.isSuccess() && result.getOrderId() != null) {
            System.out.println("PASS: Order created successfully");
            System.out.println("Order ID: " + result.getOrderId());
            System.out.println("Amount: $" + result.getTotalAmount());
        } else {
            System.out.println("FAIL: Order creation failed");
            System.out.println("Reason: " + result.getMessage());
        }
        System.out.println();
    }

    static void testFailedOrderInventory() {
        System.out.println("Test 2: Failed Order (Inventory Issue)");
        System.out.println("-".repeat(45));

        OrderProcessingFacade facade = new OrderProcessingFacade();
        var request = new OrderProcessingFacade.OrderRequest(
                "PROD456", 20, "4111111111111111", "test@example.com"
        );

        var result = facade.placeOrder(request);

        if (!result.isSuccess() && result.getMessage().contains("not available")) {
            System.out.println("PASS: Correctly detected inventory issue");
            System.out.println("Reason: " + result.getMessage());
        } else {
            System.out.println("FAIL: Should have failed due to inventory");
        }
        System.out.println();
    }

    static void testFailedOrderPayment() {
        System.out.println("Test 3: Failed Order (Payment Issue)");
        System.out.println("-".repeat(45));

        OrderProcessingFacade facade = new OrderProcessingFacade();
        var request = new OrderProcessingFacade.OrderRequest(
                "PROD123", 1, "9999111122223333", "test@example.com"  // Invalid card
        );

        var result = facade.placeOrder(request);

        if (!result.isSuccess() && result.getMessage().contains("Payment")) {
            System.out.println("PASS: Correctly detected payment issue");
            System.out.println("Reason: " + result.getMessage());
        } else {
            System.out.println("FAIL: Should have failed due to payment");
        }
        System.out.println();
    }
}