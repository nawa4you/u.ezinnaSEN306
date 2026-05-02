public class BookingFacade {
    private RoomService rooms;
    private PaymentService payment;
    private LoyaltyPoints loyalty;
    private EmailService email;

    public BookingFacade() {
        this.rooms = new RoomService();
        this.payment = new PaymentService();
        this.loyalty = new LoyaltyPoints();
        this.email = new EmailService();
    }

    public boolean bookRoom(String guest, String roomType, double price) {
        if (!rooms.isAvailable(roomType)) return false;
        if (!payment.charge(guest, price)) return false;
        
        rooms.book(roomType, guest);
        loyalty.addPoints(guest, (int)price);
        email.sendConfirmation(guest, roomType);
        
        return true;
    }
    
    /* 
     * 3 REASONS THIS CODE NEEDED A FACADE

     * -  tight Coupling:the client code knew too many subsystem classes 
     *  making it brittle if they changed
     * 
     *  - the client interacts with many subsystems directly, making it hard to test unit modules
     *
     * 
     * 
     * -   duplicated logic, nested 
     *    conditionals and error handling, just adds unnecessary complexity
     * 
     */
}

