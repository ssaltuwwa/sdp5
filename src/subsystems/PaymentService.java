package subsystems;

public class PaymentService {
    public boolean processPayment(String cardNumber, double amount) {
        System.out.println("Processing payment of $" + amount +
                " for card: " + maskCardNumber(cardNumber));
        // Simulate payment gateway integration
        return amount > 0 && cardNumber.length() == 16 && !cardNumber.startsWith("9999");
    }

    public String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() < 16) return "****";
        return cardNumber.substring(0, 4) + "****" + cardNumber.substring(12);
    }
}