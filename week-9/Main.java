 

 
public class Main {
    public static void main(String[] args) {
        System.out.println("Testing the ADT");
        
        QueueADT queue = new ArrayListQueue();
        queue.enqueue(10);
        queue.enqueue(20);
        System.out.println("ArrayListQueue Dequeue: " + queue.dequeue());

        queue = new LinkedQueue(); 
        queue.enqueue(30);
        queue.enqueue(40);
        System.out.println("LinkedQueue Dequeue: " + queue.dequeue());
        System.out.println();




        

        System.out.println("Testin the flow for exercise 1 and 2");
        OverdraftAccount account = new OverdraftAccount();
        
        account.deposit(10000);   
        account.withdraw(2000);  
        account.withdraw(7000);  
        account.withdraw(1500);  
    }
}