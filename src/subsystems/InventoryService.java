package subsystems;

public class InventoryService {
    public boolean isAvailable(String productId, int quantity) {
        System.out.println("Checking inventory for product: " + productId + ", quantity: " + quantity);
        // Simulate database check
        return quantity <= 10; // Simple logic for demonstration
    }

    public double getProductPrice(String productId) {
        System.out.println("Getting price for product: " + productId);
        // Simulate price lookup
        return switch (productId) {
            case "PROD123" -> 25.99;
            case "PROD456" -> 15.50;
            case "PROD789" -> 99.99;
            default -> 19.99;
        };
    }

    public void updateInventory(String productId, int quantity) {
        System.out.println("Updating inventory for " + productId + ", reduced by " + quantity);
    }
}