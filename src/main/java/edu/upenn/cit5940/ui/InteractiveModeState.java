package edu.upenn.cit5940.ui;

import java.util.Scanner;

public class InteractiveModeState implements AppState {
    @Override
    public void handleInput(TechNewsApp app, Scanner scanner) {
        System.out.println("==================================================");
        System.out.println("                 INTERACTIVE MODE                 ");
        System.out.println("==================================================");
        System.out.println("This mode will guide you through each operation step by step.");
        System.out.println("----------------------------------------");
        System.out.println("               AVAILABLE SERVICES       ");
        System.out.println("----------------------------------------");
        System.out.println("1. Search Articles");
        System.out.println("2. Get Autocomplete Suggestions");
        System.out.println("3. View Top Topics");
        System.out.println("4. Analyze Topic Trends");
        System.out.println("5. Browse Articles by Date");
        System.out.println("6. View Specific Article by ID");
        System.out.println("7. Show Statistics");
        System.out.println("8. Back to Main Menu");
        System.out.println("----------------------------------------");
        System.out.print("Select a service (1-8): ");

        String input = scanner.nextLine().trim();

        // handle return to menu command
        if (input.equals("8")) {
            System.out.println("Returning to Main Menu...\n");
            app.changeState(new MainMenuState());
        }
    }
}