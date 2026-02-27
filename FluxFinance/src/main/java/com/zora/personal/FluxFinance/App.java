package com.zora.personal.FluxFinance;
import com.zora.personal.FluxFinance.models.Expense;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Create a simple stream of our new Expense record
    	PublishSubject<Expense> auditFeed = PublishSubject.create();
    	
    	// 2. Define the "Audit Pipeline" (The logic)
        auditFeed
            .filter(e -> e.amount() > 500) // Filter: Only high-value items
            .map(e -> "⚠️ AUDIT ALERT: " + e.title() + "," + e.description() + " [₱" + e.amount() + "]")
            .subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Audit Feed Closed.")
            );
        
        Scanner scan = new Scanner(System.in);
        System.out.println("FluxFinance Live Audit Started. Type 'exit' to stop.");
        
        while(true) {
        	System.out.println("Enter Expense (Title, Description, Amount):");
        	String input = scan.nextLine();
        	
        	if(input.equalsIgnoreCase("exit")) break;
        	
        	try {
        		String[] parts = input.split(",");
            	String title = parts[0];
            	String desc = parts[1];
            	double amt  = Double.parseDouble(parts[2].trim());
            	
            	auditFeed.onNext(new Expense(title, desc, amt, "General"));
        	} catch(Exception e) {
        		System.out.println("Format error! Use: Title, Description, Amount");
        	}
        }
        
        auditFeed.onComplete();
        scan.close();
    }
}