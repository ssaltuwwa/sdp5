package subsystems;

public class NotificationService {
    public void sendOrderConfirmation(String email, String orderId) {
        System.out.println("Sending order confirmation to: " + email +
                " for order: " + orderId);
    }

    public void sendShippingNotification(String email, String trackingNumber) {
        System.out.println("Sending shipping notification to: " + email +
                ", tracking: " + trackingNumber);
    }

    public void sendPaymentConfirmation(String email, String transactionId) {
        System.out.println("Sending payment confirmation to: " + email +
                ", transaction: " + transactionId);
    }
}