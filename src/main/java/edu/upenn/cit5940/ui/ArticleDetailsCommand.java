package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.processor.ArticleProcessor;

class ArticleDetailsCommand implements Command {

    private final ArticleProcessor processor;

    public ArticleDetailsCommand(ArticleProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void execute(String[] args) {
        // validate there is only one argument
        if (args.length != 1) {
            System.out.println("Error: Invalid article ID input. Usage: article <id>");
            return;
        }

        String uri = args[0].trim();

        // make call to processor layer
        Article article = processor.getArticleDetails(uri);

        // format output
        if (article == null) {
            System.out.println("Article with URI '" + uri + "' not found.");
        } else {
            System.out.println("==================================================");
            System.out.println("                 ARTICLE DETAILS                  ");
            System.out.println("==================================================");
            System.out.println("  ID/URI   : " + article.getUri());
            System.out.println("  TITLE    : " + article.getTitle());
            System.out.println("  DATE     : " + article.getDate());
            System.out.println("  BODY     : " + article.getBody());
        }
        System.out.println("==================================================");
    }
}
