package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.Article;
import edu.upenn.cit5940.common.dto.CSVFormatException;

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
    }
}