package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.processor.ArticleProcessor;

class AutocompleteCommand implements Command {
    private final ArticleProcessor processor;

    public AutocompleteCommand(ArticleProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Missing autocomplete prefix. Usage: autocomplete <prefix>");
            return;
        }
        // TODO: call function in ArticleProcessor and then format and print result
        System.out.println("Executing autocomplete for given prefix...");
    }
}
