package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

class TopicsCommand implements Command {
    private final ArticleProcessor processor;

    public TopicsCommand(ArticleProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Missing month and year (YYYY-MM). Usage: topics <period>");
            return;
        }
        // TODO: call topics search
        System.out.println("Executing search for top topics in the given month...");
    }
}
