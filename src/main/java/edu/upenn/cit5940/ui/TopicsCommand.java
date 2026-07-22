package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.common.dto.TopTopicInfo;
import edu.upenn.cit5940.processor.ArticleProcessor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class TopicsCommand implements Command {
    private final ArticleProcessor processor;

    public TopicsCommand(ArticleProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void execute(String[] args) {

        // validate that there is only one argument
        if (args.length != 1) {
            System.out.println("Error: Missing period parameter. Usage: topics <period>");
            return;
        }

        String period = args[0];

        // period must match YYYY-MM
        String periodRegex = "^\\d{4}-(0[1-9]|1[0-2])$";

        if (!period.matches(periodRegex)) {
            System.out.println("Error: Invalid period format '" + period + "'. Must be YYYY-MM (e.g., 2024-04).");
            return;
        }

        // make call to processing layer
        List<TopTopicInfo> topTopics = processor.calculateTopTopics(period);

        // format output
        if (topTopics.size()==0) {
            System.out.println("No articles or words found for the specified month.");
        } else {
            System.out.println("==================================================");
            System.out.println("          TOP TOPICS FOR " + period);
            System.out.println("==================================================");
            int rank = 1;
            for(TopTopicInfo topic: topTopics){
                System.out.printf("  %2d. %-15s (%d occurrences)%n", rank++, topic.getTopicName(), topic.getMentionCount());
            }
        }
        System.out.println("==================================================");
    }
}