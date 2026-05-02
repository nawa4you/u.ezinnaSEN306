

public class OrderResult {
    private final boolean success;
    private final String trackingNumber;
    private final String message;

    public OrderResult(boolean success, String trackingNumber, String message) {
        this.success = success;
        this.trackingNumber = trackingNumber;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getMessage() { return message; }
}

public class CheckoutFacade {
    private Inventory inv;
    private Payment pay;
    private Shipping ship;
    private Email mail;

    public CheckoutFacade() {
        this.inv = new Inventory();
        this.pay = new Payment();
        this.ship = new Shipping();
        this.mail = new Email();
    }

    public OrderResult checkout(String userId, String productId, double price, String address) {
        if (!inv.checkStock(productId)) {
            return new OrderResult(false, null, "out of stock");
        }

        if (!pay.charge(userId, price)) {
            return new OrderResult(false, null, "payment failed");
        }

        inv.reserve(productId);

        if (!ship.isAvailable()) {
            pay.refund(userId, price);
            inv.release(productId);
            return new OrderResult(false, null, "shipping failed, refunded");
        }

        String label = ship.createLabel(address);
        ship.schedulePickup(label);

        mail.send(userId, "order confirmed", "tracking: " + label);

        return new OrderResult(true, label, "success");
    }
}