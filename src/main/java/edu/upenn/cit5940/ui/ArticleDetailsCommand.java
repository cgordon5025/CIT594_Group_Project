package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

class ArticleDetailsCommand implements Command {

    private final ArticleProcessor processor;

    public ArticleDetailsCommand(ArticleProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Invalid input. Usage: article <id>");
            return;
        }
        // TODO: call article search
        System.out.println("Executing search for article details...");
    }
}
