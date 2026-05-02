 

public class LegacyOrderFacade {
    
    private LegacyOrderProcessor legacySystem;

    public LegacyOrderFacade() {
        this.legacySystem = new LegacyOrderProcessor();
    }

    public void placeOrder(String customerEmail, String itemCode, double amount, String deliveryAddress) {
        legacySystem.processOrder(customerEmail, itemCode, amount, deliveryAddress);
    }
}