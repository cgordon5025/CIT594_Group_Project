package edu.upenn.cit5940.processor;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.datamanagement.ArticlesParsed;
import edu.upenn.cit5940.datamanagement.KeywordMap;
import edu.upenn.cit5940.datamanagement.NormalizeText;

import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The ArticleProcessor class holds the various methods that will be called upon by the presentation layer.
 *
 */
public class ArticleProcessor {

    public ArticleProcessor() {
    }

    public List<String> searchArticlesByKeywords(List<String> keywords) {
        // TODO: Implementation here
        Set<String> articleIds = new HashSet<>();
        Map<String, Set<String>> intersectingArticles = new HashMap<>();
        if (keywords == null || keywords.isEmpty() || ArticlesParsed.parsedArticles.size() == 0) {
            System.out.println("No Articles Found");
        }
//        String[] normalizedText = NormalizeText.normalizeText(keywords);
        for (String word : keywords) {
            if (KeywordMap.STOP_WORDS.contains(word) || word.isEmpty()) {
                continue;
            }
            if (KeywordMap.allMappedKeywords.containsKey(word)) {
                intersectingArticles.put(word, KeywordMap.allMappedKeywords.get(word));
                articleIds.addAll(KeywordMap.allMappedKeywords.get(word));
            }
        }
        if (keywords.size() > 1 && articleIds != null && articleIds.size() > 0) {
            articleIds = findIntersectingDocs(intersectingArticles);
        }
        //lastly lets get those titles
        Set<String> articleTitles = new HashSet<>();
        for (String articleId : articleIds) {
            articleTitles.add(ArticlesParsed.parsedArticles.get(articleId).getTitle());
        }
        return articleTitles.stream().toList();
//        return new ArrayList<String>();
    }

    public List<String> getAutocompleteSuggestions(String prefix) {
        // TODO: Implementation here
        if(prefix==null||prefix.trim().isEmpty()){
            return new ArrayList<>(); //return empty since there is nothing
        }
        String normalizedPrefix = prefix.trim().toLowerCase();

        return new ArrayList<String>();
    }

    public Map<String, Integer> calculateTopTopics(String period) {
        // TODO: Implementation here
        return new HashMap<String, Integer>();
    }

    public Map<String, Integer> calculateTrends(String topic, String start, String end) {
        // TODO: Implementation here

        // map to store output of month to count
        Map<String, Integer> monthlyCounts = new TreeMap<>();

        // fill map with months and initialize a count of 0
        String currentMonth = start;
        while (currentMonth.compareTo(end) <= 0) {
            monthlyCounts.put(currentMonth, 0);
            currentMonth = incrementMonth(currentMonth);
        }

        // create a submap of the treemap from start month to end month (inclusive)
        Map<String, Map<String, Integer>> relevantMonths =
                KeywordMap.topicsTree.subMap(start, end + Character.MAX_VALUE); // make the end month inclusive

        // iterate over submap and update the monthlyCounts map
        for (Map.Entry<String, Map<String, Integer>> entry : relevantMonths.entrySet()) {
            String monthKey = entry.getKey();
            Map<String, Integer> topicCount = entry.getValue();

            // retrieve the word cound
            if (topicCount != null && topicCount.containsKey(topic)) {
                int count = topicCount.get(topic);
                monthlyCounts.put(monthKey, count);
            }
        }
        return monthlyCounts;
    }

    public List<String> getArticlesByDateRange(String start, String end) {
        // TODO: Implementation here
        //need to get the closest start and end dates that exist in the set
        return ArticlesParsed.sortedArticles.stream()
                // filter articles based on inclusive start and end dates
                .filter(article -> article.getDate() != null
                        && article.getDate().compareTo(start) >= 0
                        && article.getDate().compareTo(end) <= 0)

                // extract the title
                .map(Article::getTitle)

                // and collect into a list
                .collect(Collectors.toList());
    }

    public Article getArticleDetails(String uri) {
        return ArticlesParsed.parsedArticles.get(uri);
    }

    public int getTotalArticleCount() {
        return ArticlesParsed.parsedArticles.size();
    }

    public Article getOldestArticle() {
        return ArticlesParsed.sortedArticles.first();
    }

    public Article getNewestArticle() {
        return ArticlesParsed.sortedArticles.last();
    }


    //helper to find intersecting docs from a search
    private Set<String> findIntersectingDocs(Map<String, Set<String>> intersectingArticles) {
        List<Set<String>> docIds = new ArrayList<>();
        for(Map.Entry<String, Set<String>> entry: intersectingArticles.entrySet()){
            docIds.add(entry.getValue());
        }
        Set<String> intersection = new HashSet<>(docIds.get(0));
        for(int i=1;i<docIds.size();i++){
            intersection.retainAll(docIds.get(i)); //this performs the intersection for use
        }
        return intersection;
    }

    /**
     * Helper method to increment months as a string (YYYY-MM)
     * @param yearMonth as a string
     * @return a string representing the next month
     */
    private String incrementMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));
        month++;
        // handle december -> january transition
        if (month > 12) {
            month = 1;
            year++;
        }
        return String.format("%04d-%02d", year, month); // force format to be YYYY-MM
    }
}