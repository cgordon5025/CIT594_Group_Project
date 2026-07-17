package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

class StatsCommand implements Command {
    private final ArticleProcessor processor;

    public StatsCommand(ArticleProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Invalid input. Usage: stats");
            return;
        }
        // TODO: call stats search
        System.out.println("Executing search for stats sheet...");
    }
}
