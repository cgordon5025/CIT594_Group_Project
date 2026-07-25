package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.*;

import java.util.List;

public class ProcessArticleRecord {
    public static void processRecord(List<String> rec) throws CSVFormatException {

        if (rec.getFirst() == null || rec.getFirst().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (rec.getFirst().contains("uri")) {
            return;
        }
        Article newArticle = new Article(rec);

        // fill the HashMap and TreeSet of Articles
        ArticlesParsed.parsedArticles.put(newArticle.getUri(), newArticle);
        ArticlesParsed.sortedArticles.add(newArticle);
    }
}