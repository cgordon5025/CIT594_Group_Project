package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

class SearchCommand implements Command {
    private final ArticleProcessor processor;

    public SearchCommand(ArticleProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Missing search keywords. Usage: search <keyword(s)>");
            return;
        }
        // Logic: combine args into multi-keyword string array, perform case-insensitive AND matching
        System.out.println("Executing search for keys...");
    }
}
