package edu.upenn.cit5940;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.datamanagement.*;
import edu.upenn.cit5940.ui.*;
import edu.upenn.cit5940.ui.TechNewsApp;

import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args){

        System.out.println("=== Tech News Search Engine ===");
        System.out.println("Initializing n-tier architecture...");

        // default file names
        var dataFilePath = "articles.csv";
        var logFilePath = "tech_news_search.log";

        // optional first argument for data file path
        if (args.length > 0) {
            dataFilePath = args[0];
        } else {
            // check if the root contains the default articles.csv,
            // otherwise check data subfolder
            File rootData = new File("articles.csv");
            File subfolderData = new File("data/articles.csv");

            if (!rootData.exists() && subfolderData.exists()) {
                dataFilePath = subfolderData.getPath();
            }
        }

        // optional second argument for log file path
        if (args.length > 1) {
            logFilePath = args[1];
        } else {
            // check for an existing log file, otherwise create new log file in root
            File subfolderLog = new File("logs/tech_news_search.log");
            if (subfolderLog.getParentFile() != null && subfolderLog.getParentFile().exists()) {
                logFilePath = subfolderLog.getPath();
            }
        }

        System.out.println("Loading articles from: " + dataFilePath);
        System.out.println("Log output to: " + logFilePath);


        // figure out which parsing strategy to use
        try {
            File dataFile = new File(dataFilePath);

            // factory will select proper parsing strategy
            ArticleParserStrategy parser = ParserStrategyFactory.getStrategy(dataFile);

            // parse the file and generate the map of Articles
            parser.parse(dataFile);
            KeywordMap.buildGraphFromArticles();
            //trie is made only using titles
            KeywordMap.insertListToTrie(
                    ArticlesParsed.parsedArticles.values().stream().map(Article::getTitle)
                            .flatMap(title -> Arrays.stream(NormalizeText.normalizeText(title)))
                            .toArray(String[]::new));
            // initialize treemap for month to topic count
            KeywordMap.buildTreeFromArticles();
            System.out.println(ArticlesParsed.parsedArticles.size() + " articles loaded");
            System.out.println("Architecture initialization complete!\n");

        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            // catches file issues
            System.err.println("\n[Configuration Error] " + e.getMessage());
        } catch (Exception e) {
            // catch other errors during parsing
            System.err.println("\n[Processing Error] Parsing failed: " + e.getMessage());
        }

        // once all data is initialized, instantiate the app
        TechNewsApp app = new TechNewsApp();

        // handle the main loop of the application
        app.run();
    }
}