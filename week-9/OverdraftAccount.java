 
 
public class OverdraftAccount extends BankAccount {
    private static final double limit_overdraft = -500.0;

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) return;
        
        double newBalance = balance - amount; 
        if (newBalance >= limit_overdraft) {
            balance = newBalance;
            System.out.println("Withdrew " + amount + ", new balance: " + balance);
        } else {
            System.out.println("Overdraft limit exceeded");
        }
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        System.out.println("Deposited " + amount + ", new balance: " + balance);
    }
}