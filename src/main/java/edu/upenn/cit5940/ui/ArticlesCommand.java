package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

class ArticlesCommand implements Command {
    private final ArticleProcessor processor;

    public ArticlesCommand(ArticleProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Invalid. Usage: articles <start_date> <end_date>");
            return;
        }
        // TODO: call articles search
        System.out.println("Executing search for all articles published between the given dates...");
    }
}
