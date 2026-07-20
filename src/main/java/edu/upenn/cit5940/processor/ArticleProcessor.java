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
        List<String> builtWords = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder(normalizedPrefix);
        KeywordMap.Node endOfPrefixNode = KeywordMap.findEndOfPrefixNode(normalizedPrefix);
        traverseTrieForWords(endOfPrefixNode,builtWords, currentWord);

            //NOTE: NEED TO TRAVERSE TRIE STARTING FROM FINDING PREFIX, AND FINDS THE FIRST 10 (OR LESS IF NONE EXIST) WORDS THAT HAVE THAT PREFIX
        return builtWords;
    }
    private void traverseTrieForWords(KeywordMap.Node node, List<String> finalWords, StringBuilder word){
        if(node == null) return;
        if(node.endOfWord){
            finalWords.add(word.toString());
        }
        for(Map.Entry<Character, KeywordMap.Node> nodeEntry: node.children.entrySet()){
            word.append(nodeEntry.getKey());
            traverseTrieForWords(nodeEntry.getValue(),finalWords, word);
            word.deleteCharAt(word.length()-1);
            if(finalWords.size()>=10){
                return;
            }
        }
    }

    public Map<String, Integer> calculateTopTopics(String period) {
        // TODO: Implementation here
        return new HashMap<String, Integer>();
    }

    public Map<String, Integer> calculateTrends(String topic, String start, String end) {
        // TODO: Implementation here
        //NOTE: DO WE CALCULATE VIA WORD FREQ. & TIME PERIOD?
        return new HashMap<String, Integer>();
    }

    public List<String> getArticlesByDateRange(String start, String end) {
        // TODO: Implementation here
        //need to get the closest start and end dates that exist in the set
        //also need to validate if the start/end dates are real dates (i.e. feb 31 does not exist)

        return new ArrayList<String>();
    }

    public Article getArticleDetails(String uri) {
        return ArticlesParsed.parsedArticles.get(uri);
    }

    public int getTotalArticleCount() {
        return ArticlesParsed.parsedArticles.size();
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
}