package edu.upenn.cit5940.processor;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.datamanagement.ArticlesParsed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ArticleProcessor class holds the various methods that will be called upon by the presentation layer.
 *
 */
public class ArticleProcessor {

    public ArticleProcessor() {

    }

    public List<String> searchArticlesByKeywords(List<String> keywords) {
        // TODO: Implementation here
        return new ArrayList<String>();
    }

    public List<String> getAutocompleteSuggestions(String prefix) {
        // TODO: Implementation here
        return new ArrayList<String>();
    }

    public Map<String, Integer> calculateTopTopics(String period) {
        // TODO: Implementation here
        return new HashMap<String, Integer>();
    }

    public Map<String, Integer> calculateTrends(String topic, String start, String end) {
        // TODO: Implementation here
        return new HashMap<String, Integer>();
    }

    public List<String> getArticlesByDateRange(String start, String end) {
        // TODO: Implementation here
        return new ArrayList<String>();
    }

    public Article getArticleDetails(String uri) {
        return ArticlesParsed.parsedArticles.get(uri);
    }

    public int getTotalArticleCount() {
        return ArticlesParsed.parsedArticles.size();
    }

}
