package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

class CommandModeState implements AppState {
    private final Map<String, Command> commands = new HashMap<>();

    // Accept the app instance to tap into the root-initialized components
    public CommandModeState(TechNewsApp app) {
        System.out.println("==================================================");
        System.out.println("                  COMMAND MODE                    ");
        System.out.println("==================================================");
        System.out.println("Enter commands directly. Type 'help' for available commands.");
        System.out.println("Type 'menu' to return to the main menu.");

        // Grab the single, root-level processor instance
        ArticleProcessor processor = app.getProcessor();

        // Inject that same root instance straight down into the commands
        commands.put("search", new SearchCommand(processor));
        commands.put("autocomplete", new AutocompleteCommand(processor));
        commands.put("topics", new TopicsCommand(processor));
        commands.put("trends", new TrendsCommand(processor));
        commands.put("articles", new ArticlesCommand(processor));
        commands.put("article", new ArticleDetailsCommand(processor));
        commands.put("stats", new StatsCommand(processor));
        commands.put("help", new HelpCommand());
    }

    @Override
    public void handleInput(TechNewsApp app, Scanner scanner) {
        System.out.print("> ");
        String line = scanner.nextLine().trim();

        if (line.isEmpty()) {
            System.out.println("Error: Please enter a valid command.");
            return;
        }

        String[] parts = line.split("\\s+");
        String cmdKeyword = parts[0].toLowerCase();

        if (cmdKeyword.equals("menu")) {
            System.out.println("Returning to Main Menu...\n");
            app.changeState(new MainMenuState());
            return;
        }

        Command command = commands.get(cmdKeyword);
        if (command == null) {
            System.out.println("Error: Unknown command '" + cmdKeyword + "'. Type 'help' for a list of commands.");
            return;
        }

        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        command.execute(args);
    }
}