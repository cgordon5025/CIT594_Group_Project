package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

class TrendsCommand implements Command {
    private final ArticleProcessor processor;

    public TrendsCommand(ArticleProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Invalid input. Usage: trends <topic> <start> <end>");
            return;
        }
        // TODO: call trends search
        System.out.println("Executing search for the frequency of the topic in the given time period...");
    }
}
