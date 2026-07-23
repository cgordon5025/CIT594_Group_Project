package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.datamanagement.KeywordMap;
import edu.upenn.cit5940.logging.Logger;
import edu.upenn.cit5940.processor.ArticleProcessor;

import java.util.Map;

class TrendsCommand implements Command {
    private final ArticleProcessor processor;
Logger logger = Logger.getInstance();
    public TrendsCommand(ArticleProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void execute(String[] args) {

        // argument must have exactly 3 parts
        if (args.length != 3) {
            logger.LogInformation(String.format("Invalid arguments. Usage: trends <topic> <start_period> <end_period>"), Logger.LogStatus.ERROR);
            System.out.println("Error: Invalid arguments. Usage: trends <topic> <start_period> <end_period>");
            return;
        }

        String topic = args[0].toLowerCase();
        String startPeriod = args[1];
        String endPeriod = args[2];
        logger.LogInformation(String.format("User search for trends <%s> between start_date <%s> end_date <%s>", topic,startPeriod, endPeriod), Logger.LogStatus.INFO);

        // date format must match YYYY-MM
        // months must be 01 through 12. years must be exactly 4 digits
        String periodRegex = "^\\d{4}-(0[1-9]|1[0-2])$";

        if (!startPeriod.matches(periodRegex)) {
            logger.LogInformation(String.format("Invalid start period syntax %s", startPeriod), Logger.LogStatus.ERROR);

            System.out.println("Error: Invalid start period format '" + startPeriod + "'. Must be YYYY-MM (e.g., 2024-04).");
            return;
        }

        if (!endPeriod.matches(periodRegex)) {
            logger.LogInformation(String.format("Invalid end period syntax %s", endPeriod), Logger.LogStatus.ERROR);
            System.out.println("Error: Invalid end period format '" + endPeriod + "'. Must be YYYY-MM (e.g., 2024-12).");
            return;
        }

        // start date must be before end date
        if (startPeriod.compareTo(endPeriod) > 0) {
            logger.LogInformation(String.format("Start period  <%s> cannot be after end_period <%s>",startPeriod, endPeriod), Logger.LogStatus.ERROR);
            System.out.println("Error: Start period (" + startPeriod + ") cannot be after end period (" + endPeriod + ").");
            return;
        }

        // make call to processing layer
        Map<String, Integer> trendResults = processor.calculateTrends(topic, startPeriod, endPeriod);

        if (trendResults.isEmpty()) {
            System.out.println("No trends found for topic in the specified period.");
        } else {
            System.out.println("==================================================");
            System.out.println("MONTHLY FREQUENCY FOR TOPIC: '" + topic.toUpperCase() + "'");
            System.out.println("BETWEEN " + startPeriod + " AND " + endPeriod);
            System.out.println("==================================================");

            // iterate through map of dates/counts
            for (Map.Entry<String, Integer> entry : trendResults.entrySet()) {
                System.out.println("  " + entry.getKey() + " : " + entry.getValue() + " occurrences");
            }
        }
        System.out.println("==================================================");
    }
}