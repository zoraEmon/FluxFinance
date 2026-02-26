package com.zora.personal.FluxFinance;
import io.reactivex.rxjava3.core.Observable;
import com.zora.personal.FluxFinance.models.Expense;

public class App {
    public static void main(String[] args) {
        // Create a simple stream of our new Expense record
        Observable<Expense> expenseStream = Observable.just(
            new Expense("Stickers", "For stickers", 250.0, "Logistics"),
            new Expense("Seminar Venue", "For stickers", 1500.0, "Events"),
            new Expense("Coffee", "For stickers", 120.0,  "Admin")
        );

        System.out.println("--- JPCS Audit Feed Initialized ---");

        // Subscribe to the feed
        expenseStream
            .filter(e -> e.amount() > 500) // The "Audit Alert" Logic
            .map(e -> "FLAGGED HIGH VALUE: " + e.title() + ", " + e.description()  + " [₱" + e.amount() + "]")
            .subscribe(System.out::println);
    }
}