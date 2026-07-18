package edu.upenn.cit5940.ui;

import java.util.Scanner;

public class HelpModeState implements AppState {
    @Override
    public void handleInput(TechNewsApp app, Scanner scanner) {
        System.out.println("============================================================");
        System.out.println("                    HELP & DOCUMENTATION                    ");
        System.out.println("============================================================");
        System.out.println("INTERACTIVE MODE:");
        System.out.println("  • Guided step-by-step interface");
        System.out.println("  • Prompts for all required inputs");
        System.out.println("  • Perfect for beginners");
        System.out.println("\nCOMMAND MODE:");
        System.out.println("  • Direct command entry");
        System.out.println("  • Faster for experienced users");
        System.out.println("  • Type 'help' for command list");
        System.out.println("\nAVAILABLE SERVICES:");
        System.out.println("  1. Search Articles - Find articles by keywords");
        System.out.println("  2. Autocomplete - Get search suggestions");
        System.out.println("  3. Top Topics - View trending topics by period");
        System.out.println("  4. Topic Trends - Analyze topic popularity over time");
        System.out.println("  5. Browse Articles - Filter articles by date range");
        System.out.println("  6. View Article - Get detailed article information");
        System.out.println("  7. Statistics - View database statistics");
        System.out.println("\nDATE FORMATS:");
        System.out.println("  • Period: YYYY-MM (e.g., 2023-12)");
        System.out.println("  • Date: YYYY-MM-DD (e.g., 2023-12-01)");
        System.out.println("============================================================");
        System.out.print("Press Enter to return to the main menu...");

        // pause to let the user read
        scanner.nextLine();

        // go back to main menu
        app.changeState(new MainMenuState());
    }
}