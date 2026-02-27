package com.zora.personal.FluxFinance.services;

import com.zora.personal.FluxFinance.models.Expense;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class AuditService {
	private final PublishSubject<Expense> expenseStream = PublishSubject.create();
	
	public AuditService() {
		setupAlertPipeline();
		setupAccountinPipeline();
	}

	//This will be triggered when the expense record created.
	public void setupAlertPipeline() {
		expenseStream
			.filter(e -> e.amount() > 250)
			.map(e -> "⚠ Audit Alert: " + e.title() + " >= " + e.amount())
			.subscribe(
						System.out::println,
		                err -> System.err.println("Pipeline Error: " + err.getMessage()
					));
			
	}
	
	public void setupAccountinPipeline() {
		expenseStream
			.map(Expense::amount)
			.scan(0.0, (total, current) -> total + current)
			.subscribe(
					runningTotal -> System.out.printf("📊 Current Org Spend: ₱%.2f%n", runningTotal)
					);
	}
	
	//Used to push data to the observer.
	public void processTransaction(Expense expense) {
		expenseStream.onNext(expense);
	}
	
	public void shutdown() {
		expenseStream.onComplete();
	}
}
