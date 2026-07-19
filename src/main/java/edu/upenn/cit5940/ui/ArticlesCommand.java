package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

class ArticlesCommand implements Command {
    private final ArticleProcessor processor;

    // strict datetimeformatter to validate proper date entries
    private static final DateTimeFormatter STRICT_FORMATTER = DateTimeFormatter
            .ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);

    public ArticlesCommand(ArticleProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void execute(String[] args) {

        // validate argument count = 2
        if (args.length != 2) {
            System.out.println("Error: Invalid arguments. Usage: articles <start_date> <end_date>");
            return;
        }

        String startDate = args[0];
        String endDate = args[1];

        // startDate and endDate must match YYYY-MM-DD
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        if (!startDate.matches(dateRegex)) {
            System.out.println("Error: Invalid start date syntax '" + startDate + "'. Must be YYYY-MM-DD.");
            return;
        }
        if (!endDate.matches(dateRegex)) {
            System.out.println("Error: Invalid end date syntax '" + endDate + "'. Must be YYYY-MM-DD.");
            return;
        }

        // validate given dates through strict datetimeformatter
        LocalDate startLocalDate;
        LocalDate endLocalDate;

        // handle start date logical validation
        try {
            STRICT_FORMATTER.parse(startDate);
        } catch (DateTimeParseException e) {
            System.out.println("Error: Start date '" + startDate + "' is not a proper calendar date.");
            return;
        }

        // handle end date logical validation
        try {
            STRICT_FORMATTER.parse(endDate);
        } catch (DateTimeParseException e) {
            System.out.println("Error: End date '" + endDate + "' is not a proper calendar date.");
            return;
        }

        // startDate must be before endDate
        if (startDate.compareTo(endDate) > 0) {
            System.out.println("Error: Start date (" + startDate + ") cannot be after end date (" + endDate + ").");
            return;
        }

        // make call to getArticlesByDateRange in processor layer
        List<String> titles = processor.getArticlesByDateRange(startDate, endDate);

        // format output
        if (titles.isEmpty()) {
            System.out.println("No articles found.");
        } else {
            System.out.println("=============================================================");
            System.out.println("ARTICLES PUBLISHED BETWEEN " + startDate + " AND " + endDate);
            System.out.println("=============================================================");
            for (String title : titles) {
                System.out.println(title);
            }
            System.out.println("==================================================");
        }
    }
}
