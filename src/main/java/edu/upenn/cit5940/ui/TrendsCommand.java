package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

import java.util.Map;

class TrendsCommand implements Command {
    private final ArticleProcessor processor;

    public TrendsCommand(ArticleProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void execute(String[] args) {

        // argument must have exactly 3 parts
        if (args.length != 3) {
            System.out.println("Error: Invalid arguments. Format: trends <topic> <start_period> <end_period>");
            return;
        }

        String topic = args[0];
        String startPeriod = args[1];
        String endPeriod = args[2];

        // date format must match YYYY-MM
        // months must be 01 through 12. years must be exactly 4 digits
        String periodRegex = "^\\d{4}-(0[1-9]|1[0-2])$";

        if (!startPeriod.matches(periodRegex)) {
            System.out.println("Error: Invalid start period format '" + startPeriod + "'. Must be YYYY-MM (e.g., 2024-04).");
            return;
        }

        if (!endPeriod.matches(periodRegex)) {
            System.out.println("Error: Invalid end period format '" + endPeriod + "'. Must be YYYY-MM (e.g., 2024-12).");
            return;
        }

        // start date must be before end date
        if (startPeriod.compareTo(endPeriod) > 0) {
            System.out.println("Error: Start period (" + startPeriod + ") cannot be after end period (" + endPeriod + ").");
            return;
        }

        // make call to processing layer
        Map<String, Integer> trendResults = processor.calculateTrends(topic, startPeriod, endPeriod);

        if (trendResults.isEmpty()) {
            System.out.println("No trends found for topic in the specified period.");
        } else {
            System.out.println("==================================================");
            System.out.println(" TREND ANALYSIS FOR TOPIC: " + topic.toUpperCase());
            System.out.println("==================================================");

            // iterate through map of dates/counts
            for (Map.Entry<String, Integer> entry : trendResults.entrySet()) {
                System.out.println("  " + entry.getKey() + " : " + entry.getValue() + " occurrences");
            }
        }
        System.out.println("==================================================");
    }
}
