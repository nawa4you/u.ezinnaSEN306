// existing system and new subsystem

class TaxCalculator {
    public double calc(String state, double amount) {
        if (state != null && state.contains("CA")) {
            return amount * 0.08;
        }
        return 0.0;
    }
}

class Logger {
    public void log(String msg) {
        System.out.println(msg);
    }
}

public class CheckoutFacade {
    private Inventory inv;
    private Payment pay;
    private Shipping ship;
    private Email mail;
    private TaxCalculator taxCalc;
    private Logger logger;

    public CheckoutFacade() {
        this.inv = new Inventory();
        this.pay = new Payment();
        this.ship = new Shipping();
        this.mail = new Email();
        this.taxCalc = new TaxCalculator();
        this.logger = new Logger();
    }

    public OrderResult checkout(String userId, String productId, double price, String address) {
        logger.log("attempting checkout for " + userId);

        if (!inv.checkStock(productId)) {
            logger.log("failed: out of stock");
            return new OrderResult(false, null, "out of stock");
        }

        double tax = taxCalc.calc(address, price);
        double total = price + tax;

        if (!pay.charge(userId, total)) {
            logger.log("failed: payment declined");
            return new OrderResult(false, null, "payment failed");
        }

        inv.reserve(productId);

        if (!ship.isAvailable()) {
            pay.refund(userId, total);
            inv.release(productId);
            logger.log("failed: shipping issue, rolling back");
            return new OrderResult(false, null, "shipping failed");
        }

        String label = ship.createLabel(address);
        ship.schedulePickup(label);

        mail.send(userId, "order confirmed", "total paid: " + total + ", tracking: " + label);
        logger.log("checkout success");

        return new OrderResult(true, label, "success");
    }
}