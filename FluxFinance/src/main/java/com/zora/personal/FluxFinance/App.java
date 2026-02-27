package com.zora.personal.FluxFinance;

import java.util.Scanner;

import com.zora.personal.FluxFinance.models.Expense;
import com.zora.personal.FluxFinance.services.AuditService;

public class App {
    public static void main(String[] args) {
        AuditService auditService = new AuditService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- FluxFinance: JPCS Audit Module ---");
        
        while (true) {
            System.out.print("Enter (Title, Description, Amount) or 'exit': ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            try {
                String[] parts = input.split(",");
                auditService.processTransaction(
                    new Expense(parts[0].stripIndent(), parts[1].stripIndent(), Double.parseDouble(parts[2].trim()), "General")
                );
            } catch (Exception e) {
                System.out.println("Invalid format. Try: Stickers, 250");
            }
        }

        auditService.shutdown();
        scanner.close();
    }
}