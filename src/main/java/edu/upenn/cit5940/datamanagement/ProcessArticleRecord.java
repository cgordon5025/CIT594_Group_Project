package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.*;

import java.util.List;

public class ProcessArticleRecord {
    public static void processRecord(List<String> rec) throws CSVFormatException {
        // TODO: Add code here
        if (rec.getFirst() == null || rec.getFirst().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (rec.getFirst().contains("uri")) {
            return;
        }
        Article newArticle = new Article(rec);
        ArticlesParsed.parsedArticles.put(newArticle.getUri(), newArticle);
        ArticlesParsed.articlesPubDates.add(new ArticlesParsed.ArticleDate(newArticle.getDate(), newArticle.getTitle()));
    }
}