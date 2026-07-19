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

        String choice = scanner.nextLine().trim();

        if (choice.isEmpty()) {
            System.out.println("Error: Selection cannot be empty. Please enter a choice.\n");
            return;
        }

        var processor = app.getProcessor();

        switch (choice) {
            case "1": // Search
                System.out.print("Enter search keyword(s) separated by spaces: ");
                String searchInput = scanner.nextLine().trim();
                if (!searchInput.isEmpty()) {
                    // split string into array of args, then call the search command
                    String[] searchArgs = searchInput.split("\\s+");
                    new SearchCommand(processor).execute(searchArgs);
                } else {
                    System.out.println("Error: Keywords cannot be empty.");
                }
                waitForEnter(scanner);
                break;

            case "2": // Autocomplete
                System.out.print("Enter word prefix to autocomplete: ");
                String prefixInput = scanner.nextLine().trim();
                // build single element arg array and call autocomplete command
                String[] autocompleteArgs = { prefixInput };
                new AutocompleteCommand(processor).execute(autocompleteArgs);
                waitForEnter(scanner);
                break;

            case "3": // topics
                System.out.print("Enter period (YYYY-MM): ");
                String periodInput = scanner.nextLine().trim();
                String[] topicsArgs = { periodInput };
                new TopicsCommand(processor).execute(topicsArgs);
                waitForEnter(scanner);
                break;

            case "4": // trends
                System.out.print("Enter topic word: ");
                String trendTopic = scanner.nextLine().trim();
                System.out.print("Enter start period (YYYY-MM): ");
                String trendStart = scanner.nextLine().trim();
                System.out.print("Enter end period (YYYY-MM): ");
                String trendEnd = scanner.nextLine().trim();

                // build 3 element ary array and call trends command
                String[] trendsArgs = { trendTopic, trendStart, trendEnd };
                new TrendsCommand(processor).execute(trendsArgs);
                waitForEnter(scanner);
                break;

            case "5": // articles
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String articlesStart = scanner.nextLine().trim();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String articlesEnd = scanner.nextLine().trim();

                // build two element array of args and call articles command
                String[] articlesArgs = { articlesStart, articlesEnd };
                new ArticlesCommand(processor).execute(articlesArgs);
                waitForEnter(scanner);
                break;

            case "6": // article
                System.out.print("Enter specific unique Article ID (URI): ");
                String articleId = scanner.nextLine().trim();

                // build single element arg array and call article command
                String[] idArgs = { articleId };
                new ArticleDetailsCommand(processor).execute(idArgs);
                waitForEnter(scanner);
                break;

            case "7": // stats
                // stats does not require any arguments
                String[] statsArgs = {};
                new StatsCommand(processor).execute(statsArgs);
                waitForEnter(scanner);
                break;

            case "8": // return to main menu
                System.out.println("Returning to Main Menu...\n");
                app.changeState(new MainMenuState());
                break;

            default:
                System.out.println("Error: '" + choice + "' is an invalid choice. Enter a valid number (1-8)).\n");
        }
    }

    /**
     * Helper method to stall execution until the user presses ENTER.
     */
    private void waitForEnter(Scanner scanner) {
        System.out.print("\nPress ENTER to return to the main Interactive Mode menu...");
        scanner.nextLine();
        System.out.println();
    }
}