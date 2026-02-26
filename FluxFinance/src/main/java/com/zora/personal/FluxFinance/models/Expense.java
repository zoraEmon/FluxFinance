package com.zora.personal.FluxFinance.models;

//A Record is perfect for immutable event data like financial transactions
public record Expense(
		String title,
		String description,
		double amount,
		String category
) {}
