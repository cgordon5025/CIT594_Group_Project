package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.datamanagement.NormalizeText;
import edu.upenn.cit5940.logging.Logger;
import edu.upenn.cit5940.processor.ArticleProcessor;

import java.util.Arrays;
import java.util.List;

class SearchCommand implements Command {
    private final ArticleProcessor processor;
    Logger logger = Logger.getInstance();
    public SearchCommand(ArticleProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void execute(String[] args) {
        // must have at least one argument
        if (args.length == 0) {
            logger.LogInformation(String.format("Invalid argument for keyword search"), Logger.LogStatus.ERROR);
            System.out.println("Error: Missing search keywords. Usage: search <keyword> <keyword(s)>");
            return;
        }

        // join string[] args into a single string
        String rawInputSentence = String.join(" ", args);

        // call the normalizetext method to clean and turn into an array of keywords
        String[] normalizedArray = NormalizeText.normalizeText(rawInputSentence);

        // wrap into an arraylist
        List<String> keywords = Arrays.asList(normalizedArray);
        logger.LogInformation(String.format("User search query: <%s>", keywords.toString()), Logger.LogStatus.INFO);

        // make call to processor layer
        List<String> matchingTitles = processor.searchArticlesByKeywords(keywords);

        // format output
        if (matchingTitles.isEmpty()) {
            System.out.println("No articles found.");
        } else {
            System.out.println("==================================================");
            System.out.println("       ARTICLES THAT CONTAIN GIVEN KEYWORDS       ");
            System.out.println("==================================================");
            for (String title : matchingTitles) {
                System.out.println(title); // Print each matching title on a new line
            }
        }
        System.out.println("==================================================");
    }
}