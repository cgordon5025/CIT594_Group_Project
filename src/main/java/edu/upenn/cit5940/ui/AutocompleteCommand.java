package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.logging.Logger;
import edu.upenn.cit5940.processor.ArticleProcessor;

import java.util.List;

class AutocompleteCommand implements Command {
    private final ArticleProcessor processor;
    Logger logger = Logger.getInstance();

    public AutocompleteCommand(ArticleProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void execute(String[] args) {
        // there must be only one argument -- the prefix
        if (args.length != 1) {
            logger.LogInformation(String.format("Error missing prefix parameter"), Logger.LogStatus.ERROR);

            System.out.println("Error: Missing prefix parameter. Usage: autocomplete <prefix>");
            return;
        }
        String prefix = args[0];
        logger.LogInformation(String.format("User auto-complete with prefix <%s>", prefix), Logger.LogStatus.INFO);

        // make call to processor layer
        List<String> suggestions = processor.getAutocompleteSuggestions(prefix);

        // format output
        if (suggestions.isEmpty()) {
            System.out.println("No matching word suggestions found.");
        } else {
            System.out.println("=============================================================");
            System.out.println("         ARTICLE MATCHES FOR " + prefix + "          ");
            System.out.println("=============================================================");

            for (String suggestion : suggestions) {
                System.out.println(suggestion);
            }
        }
        System.out.println("==================================================");
    }
}