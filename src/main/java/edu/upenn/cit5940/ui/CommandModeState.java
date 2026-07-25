package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

class CommandModeState implements AppState {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandModeState(TechNewsApp app) {
        System.out.println("==================================================");
        System.out.println("                  COMMAND MODE                    ");
        System.out.println("==================================================");
        System.out.println("Enter commands directly. Type 'help' for available commands.");
        System.out.println("Type 'menu' to return to the main menu.");

        // grab the single, root-level processor
        ArticleProcessor processor = app.getProcessor();

        // pass that single processor directly to the commands
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
            System.out.println("Error: Please enter a valid choice.");
            return;
        }

        // parse the command input string
        // first word is the command
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

        // next strings are built into an array of Strings and passed as an argument to the command's execute method
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length); // remove the first argument ie the "command"

        command.execute(args);
    }
}