package edu.upenn.cit5940.ui;

import java.util.Scanner;

class MainMenuState implements AppState {
    @Override
    public void handleInput(TechNewsApp app, Scanner scanner) {
        System.out.println("==================================================");
        System.out.println("                    MAIN MENU                     ");
        System.out.println("==================================================");
        System.out.println("1. Interactive Mode (Guided Menu)");
        System.out.println("2. Command Mode (Direct Commands)");
        System.out.println("3. Help & Documentation");
        System.out.println("4. Exit");
        System.out.println("==================================================");
        System.out.print("Please select an option (1-4): ");

        String input = scanner.nextLine().trim();

        // handle empty inputs
        if (input.isEmpty()) {
            System.out.println("Error: Input cannot be empty. Please choose an option.\n");
            return;
        }

        switch (input) {
            case "1":
                System.out.println("Entering Interactive Mode... (Not yet implemented)\n");
                break;
            case "2":
                app.changeState(new CommandModeState(app));
                break;
            case "3":
                app.changeState(new HelpModeState());
                break;
            case "4":
                app.exit();
                break;
            default:
                // handle invalid inputs
                System.out.println("Error: '" + input + "' is an invalid choice. Please enter a number between 1 and 4.\n");
        }
    }
}
