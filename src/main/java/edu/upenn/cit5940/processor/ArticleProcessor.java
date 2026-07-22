package edu.upenn.cit5940.processor;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.common.dto.TopTopicInfo;
import edu.upenn.cit5940.datamanagement.ArticlesParsed;
import edu.upenn.cit5940.datamanagement.KeywordMap;
import edu.upenn.cit5940.datamanagement.NormalizeText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
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
        if (prefix == null || prefix.trim().isEmpty()) {
            return new ArrayList<>(); //return empty since there is nothing
        }
        String normalizedPrefix = prefix.trim().toLowerCase();
        List<String> builtWords = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder(normalizedPrefix);
        KeywordMap.Node endOfPrefixNode = KeywordMap.findEndOfPrefixNode(normalizedPrefix);
        traverseTrieForWords(endOfPrefixNode, builtWords, currentWord);

        //NOTE: NEED TO TRAVERSE TRIE STARTING FROM FINDING PREFIX, AND FINDS THE FIRST 10 (OR LESS IF NONE EXIST) WORDS THAT HAVE THAT PREFIX
        return builtWords;
    }

    private void traverseTrieForWords(KeywordMap.Node node, List<String> finalWords, StringBuilder word) {
        if (node == null) return;
        if (node.endOfWord) {
            finalWords.add(word.toString());
        }
        for (Map.Entry<Character, KeywordMap.Node> nodeEntry : node.children.entrySet()) {
            word.append(nodeEntry.getKey());
            traverseTrieForWords(nodeEntry.getValue(), finalWords, word);
            word.deleteCharAt(word.length() - 1);
            if (finalWords.size() >= 10) {
                return;
            }
        }
    }

    public List<TopTopicInfo>calculateTopTopics(String period) {
        //assume that period has been validated already
        LocalDate startDate = LocalDate.parse(period.concat("-01")); //this is really dirty way to do it but it works
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        List<Article> articlesInPeriod = ArticlesParsed.sortedArticles.stream().filter(article -> isInDateWindow(LocalDate.parse(article.getDate()), startDate, endDate)).toList();
        List<String> allWordsInPeriod = articlesInPeriod.stream().map(Article::getTitle)
                .flatMap(title->Arrays.stream(NormalizeText.normalizeText(title)))
                .toList();
        PriorityQueue<TopTopicInfo> topTopicHeap = new PriorityQueue<>((a,b)-> {
            int mentionCompare = Integer.compare(b.getMentionCount(), a.getMentionCount());
            if(mentionCompare!=0){
                return  mentionCompare;
            }
            return a.getTopicName().compareTo(b.getTopicName()); //in the event of a tie sort lexoicographically
        });
        for(String word: allWordsInPeriod){
            if(KeywordMap.STOP_WORDS.contains(word) || word.length()<=1){continue;}
            if(wordInPriorityQueue(word, topTopicHeap)){
                updatePriorityQueue(word, topTopicHeap);
            }else{
                topTopicHeap.offer(new TopTopicInfo(word, 1));
            }
        }
        ArrayList<TopTopicInfo> finalList= new ArrayList<>();
        for(int i=0;i<10;i++){
            finalList.add(topTopicHeap.poll());
        }

        return finalList;
    }
    private boolean wordInPriorityQueue(String word, PriorityQueue<TopTopicInfo> priorityQueue){
        for(TopTopicInfo topTopicInfo:priorityQueue){
            if(topTopicInfo.getTopicName().equals(word)){return true;}
        }
        return false;
    }
    private void updatePriorityQueue(String word, PriorityQueue<TopTopicInfo> priorityQueue){
        TopTopicInfo targetNode = null;
        for(TopTopicInfo topTopicInfo: priorityQueue){
            if(topTopicInfo.getTopicName().equals(word)){
                targetNode = topTopicInfo;
                break;
            }
        }
        if(targetNode!=null){
            priorityQueue.remove(targetNode);
            priorityQueue.offer(new TopTopicInfo(targetNode.getTopicName(), targetNode.getMentionCount()+1));
        }
    }

    //helper to be used with .filter() to get articles in between 2 dates
    private boolean isInDateWindow(LocalDate givenDate, LocalDate startDate, LocalDate endDate) {
        if (givenDate.isAfter(startDate) && givenDate.isBefore(endDate)) {
            return true;
        }
        return false;
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
        for (Map.Entry<String, Set<String>> entry : intersectingArticles.entrySet()) {
            docIds.add(entry.getValue());
        }
        Set<String> intersection = new HashSet<>(docIds.get(0));
        for (int i = 1; i < docIds.size(); i++) {
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