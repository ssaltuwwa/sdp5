package subsystems;

public class OrderService {
    public String createOrder(String productId, int quantity, double totalPrice) {
        String orderId = "ORD" + System.currentTimeMillis();
        System.out.println("Creating order: " + orderId +
                " for product: " + productId +
                ", total: $" + totalPrice);
        return orderId;
    }

    public void updateOrderStatus(String orderId, String status) {
        System.out.println("Updating order " + orderId + " to status: " + status);
    }
}