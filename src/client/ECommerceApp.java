package client;

import facade.OrderProcessingFacade;

public class ECommerceApp {
    public static void main(String[] args) {
        System.out.println("E-COMMERCE ORDER PROCESSING SYSTEM");
        System.out.println("=====================================\n");

        // Create facade - single point of interaction
        OrderProcessingFacade orderFacade = new OrderProcessingFacade();

        // Test Case 1: Successful order
        System.out.println("TEST 1: Successful Order");
        System.out.println("-".repeat(30));
        var request1 = new OrderProcessingFacade.OrderRequest(
                "PROD123",
                2,
                "4111111111111111",
                "john.doe@example.com",
                "John Doe"
        );

        var result1 = orderFacade.placeOrder(request1);

        if (result1.isSuccess()) {
            orderFacade.notifyShipping(result1.getOrderId(),
                    "TRK789012",
                    "john.doe@example.com");
        }

        // Test Case 2: Failed order - too large quantity
        System.out.println("\nTEST 2: Failed Order - Inventory Issue");
        System.out.println("-".repeat(45));
        var request2 = new OrderProcessingFacade.OrderRequest(
                "PROD456",
                15,
                "4111111111111111",
                "jane.smith@example.com",
                "Jane Smith"
        );

        var result2 = orderFacade.placeOrder(request2);
        System.out.println("Result: " + result2.getMessage());

        // Test Case 3: Failed order - invalid card
        System.out.println("\nTEST 3: Failed Order - Payment Issue");
        System.out.println("-".repeat(45));
        var request3 = new OrderProcessingFacade.OrderRequest(
                "PROD123",
                1,
                "9999111122223333",  // Invalid card
                "bob.wilson@example.com",
                "Bob Wilson"
        );

        var result3 = orderFacade.placeOrder(request3);
        System.out.println("Result: " + result3.getMessage());

        // Test Case 4: Another successful order
        System.out.println("\nTEST 4: Another Successful Order");
        System.out.println("-".repeat(40));
        var request4 = new OrderProcessingFacade.OrderRequest(
                "PROD789",
                1,
                "4222222222222222",
                "alice.johnson@example.com",
                "Alice Johnson"
        );

        var result4 = orderFacade.placeOrder(request4);

        // Summary
        System.out.println("ORDER PROCESSING SUMMARY");
        System.out.println("Successful orders: " +
                (result1.isSuccess() ? 1 : 0) + (result4.isSuccess() ? 1 : 0));
        System.out.println("Failed orders: " +
                (!result2.isSuccess() ? 1 : 0) + (!result3.isSuccess() ? 1 : 0));
    }
}