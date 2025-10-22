package facade;

import subsystems.*;

public class OrderProcessingFacade {
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;
    private final OrderService orderService;

    public OrderProcessingFacade() {
        this.inventoryService = new InventoryService();
        this.paymentService = new PaymentService();
        this.notificationService = new NotificationService();
        this.orderService = new OrderService();
    }

    // Main simplified method - all complexity hidden
    public OrderResult placeOrder(OrderRequest request) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("STARTING ORDER PROCESS");
        System.out.println("=".repeat(40));

        // 1. Check inventory
        if (!inventoryService.isAvailable(request.getProductId(), request.getQuantity())) {
            String error = "Product not available in requested quantity";
            System.out.println(error);
            return new OrderResult(false, null, error, 0);
        }

        // 2. Calculate price
        double price = inventoryService.getProductPrice(request.getProductId());
        double totalPrice = price * request.getQuantity();
        System.out.println("Total price: $" + totalPrice);

        // 3. Process payment
        if (!paymentService.processPayment(request.getCardNumber(), totalPrice)) {
            String error = "Payment failed - please check your card details";
            System.out.println(error);
            return new OrderResult(false, null, error, totalPrice);
        }

        String transactionId = paymentService.generateTransactionId();
        System.out.println("Payment successful! Transaction: " + transactionId);

        // 4. Update inventory
        inventoryService.updateInventory(request.getProductId(), request.getQuantity());

        // 5. Create order
        String orderId = orderService.createOrder(request.getProductId(), request.getQuantity(), totalPrice);
        orderService.updateOrderStatus(orderId, "CONFIRMED");

        // 6. Send notifications
        notificationService.sendOrderConfirmation(request.getCustomerEmail(), orderId);
        notificationService.sendPaymentConfirmation(request.getCustomerEmail(), transactionId);

        System.out.println("=".repeat(40));
        System.out.println("ORDER COMPLETED SUCCESSFULLY!");
        System.out.println("Order ID: " + orderId);
        System.out.println("Transaction: " + transactionId);
        System.out.println("=".repeat(40));

        return new OrderResult(true, orderId, "Order completed successfully", totalPrice);
    }

    public void notifyShipping(String orderId, String trackingNumber, String customerEmail) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("SHIPPING NOTIFICATION");
        System.out.println("=".repeat(40));
        notificationService.sendShippingNotification(customerEmail, trackingNumber);
        System.out.println("Order " + orderId + " has been shipped!");
        System.out.println("Tracking number: " + trackingNumber);
        System.out.println("=".repeat(40));
    }

    // Supporting classes for clean data transfer
    public static class OrderRequest {
        private final String productId;
        private final int quantity;
        private final String cardNumber;
        private final String customerEmail;
        private final String customerName;

        public OrderRequest(String productId, int quantity, String cardNumber, String customerEmail) {
            this(productId, quantity, cardNumber, customerEmail, "Customer");
        }

        public OrderRequest(String productId, int quantity, String cardNumber,
                            String customerEmail, String customerName) {
            this.productId = productId;
            this.quantity = quantity;
            this.cardNumber = cardNumber;
            this.customerEmail = customerEmail;
            this.customerName = customerName;
        }

        // Getters
        public String getProductId() { return productId; }
        public int getQuantity() { return quantity; }
        public String getCardNumber() { return cardNumber; }
        public String getCustomerEmail() { return customerEmail; }
        public String getCustomerName() { return customerName; }
    }

    public static class OrderResult {
        private final boolean success;
        private final String orderId;
        private final String message;
        private final double totalAmount;

        public OrderResult(boolean success, String orderId, String message, double totalAmount) {
            this.success = success;
            this.orderId = orderId;
            this.message = message;
            this.totalAmount = totalAmount;
        }

        // Getters
        public boolean isSuccess() { return success; }
        public String getOrderId() { return orderId; }
        public String getMessage() { return message; }
        public double getTotalAmount() { return totalAmount; }

        @Override
        public String toString() {
            return String.format("OrderResult{success=%s, orderId=%s, message='%s', total=$%.2f}",
                    success, orderId, message, totalAmount);
        }
    }
}