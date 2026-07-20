package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.processor.ArticleProcessor;

class StatsCommand implements Command {
    private final ArticleProcessor processor;

    public StatsCommand(ArticleProcessor processor) {
        this.processor = processor;
    }
    @Override
    public void execute(String[] args) {
        // validate there are no arguments
        if (args.length > 0) {
            System.out.println("Error: The 'stats' command does not accept arguments. Usage: stats");
            return;
        }

        // make call to processor layer
        int totalArticles = processor.getTotalArticleCount();
        Article oldest = processor.getOldestArticle();
        Article newest = processor.getNewestArticle();

        // TODO: add other metrics as needed (unique word count, oldest article, newest article, etc)

        // format output
        System.out.println("==================================================");
        System.out.println("                DATABASE STATISTICS               ");
        System.out.println("==================================================");
        System.out.printf("  Total Articles Loaded : %d%n", totalArticles);
        System.out.printf("  Oldest Article Date   : %s%n", oldest.getDate());
        System.out.printf("  Oldest Article Title  : %s%n", oldest.getTitle());
        System.out.printf("  Newest Article Date   : %s%n", newest.getDate());
        System.out.printf("  Newest Article Title  : %s%n", newest.getTitle());
        System.out.println("==================================================");
    }
}
