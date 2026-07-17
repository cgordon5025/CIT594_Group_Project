package edu.upenn.cit5940;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.datamanagement.ArticleParserStrategy;
import edu.upenn.cit5940.datamanagement.ArticlesParsed;
import edu.upenn.cit5940.datamanagement.ParserStrategyFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

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

        System.out.println("=== Starting Tech News Search application ===");
        System.out.println("Data Source: " + dataFilePath);
        System.out.println("Log Output : " + logFilePath);
        System.out.println("============================================");

        // figure out which parsing strategy to use
        try {
            File dataFile = new File(dataFilePath);

            // factory will select proper parsing strategy
            ArticleParserStrategy parser = ParserStrategyFactory.getStrategy(dataFile);

            // parse the file and generate the map of Articles
            parser.parse(dataFile);

//            System.out.println("\n[Success] Loaded " + articles.size() + " articles.");

        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            // catches file issues
            System.err.println("\n[Configuration Error] " + e.getMessage());
        } catch (Exception e) {
            // catch other errors during parsing
            System.err.println("\n[Processing Error] Parsing failed: " + e.getMessage());
        }
    }
}