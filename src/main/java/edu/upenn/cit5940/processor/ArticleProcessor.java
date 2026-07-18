package edu.upenn.cit5940.processor;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.datamanagement.ArticlesParsed;
import edu.upenn.cit5940.datamanagement.KeywordMap;
import edu.upenn.cit5940.datamanagement.NormalizeText;

import java.util.*;

/**
 * The ArticleProcessor class holds the various methods that will be called upon by the presentation layer.
 *
 */
public class ArticleProcessor {

    public ArticleProcessor() {


    }

    public Set<String> keywordSearch(String rawKeywordSearch) {
        Set<String> articleIds = new HashSet<>();
        Map<String, Set<String>> intersectingArticles = new HashMap<>();
        if (rawKeywordSearch == null || rawKeywordSearch.trim().isEmpty() || ArticlesParsed.parsedArticles.size() == 0) {
            System.out.println("No Articles Found");
        }
        String[] normalizedText = NormalizeText.normalizeText(rawKeywordSearch);
        for (String word : normalizedText) {
            if (KeywordMap.STOP_WORDS.contains(word) || word.isEmpty()) {
                continue;
            }
            if (KeywordMap.allMappedKeywords.containsKey(word)) {
                intersectingArticles.put(word, KeywordMap.allMappedKeywords.get(word));
                articleIds.addAll(KeywordMap.allMappedKeywords.get(word));
            }
        }
        if (normalizedText.length > 1 && articleIds != null && articleIds.size() > 0) {
            articleIds = findIntersectingDocs(intersectingArticles);
        }
        //lastly lets get those titles
        Set<String> articleTitles = new HashSet<>();
        for (String articleId : articleIds) {
            articleTitles.add(ArticlesParsed.parsedArticles.get(articleId).getTitle());
        }
        return articleTitles;

    }

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
}