package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.*;

import java.util.*;

public class ArticlesParsed {
    public static Map<String, Article> parsedArticles = new HashMap<>();
    public static Map<String, Set<String>> allMappedKeywords = new HashMap<>();

    public static final Set<String> STOP_WORDS = TextFileReader.readTXTFile("data/stop_words.txt");
    //    RECYCLED FROM HW 6
    private static String[] normalizeText(String text) {
        return text.trim().toLowerCase().replaceAll("[^a-z0-9\\s-]", " ")
                .replaceAll("^-+", "") //remove leading -
                .replaceAll("-+$", "") //remove trailing -
                .replaceAll("\\s-+", " ") //remove trailing - after space
                .replaceAll("-+\\s", " ") //remove leading - b4 space
                .split(" ");// split at the spaces
    }
    //recycled from HW 9
    public static void buildGraphFromArticles() {
        for (Article article : parsedArticles.values()) {
            var articleTitle = article.getTitle();
            if (articleTitle.isEmpty()) continue;
            var titleNormalized = normalizeText(Arrays.toString(articleTitle.split(" ")));

            var articleUri = article.getUri();
            for (String wordInTitle : titleNormalized) {
                if (STOP_WORDS.contains(wordInTitle) || wordInTitle.isEmpty()) continue;
//               List<String> filteredWords = Arrays.stream(titleNormalized)
//                       .filter(word->!word.equals(wordInTitle) && !word.isEmpty())
//                       .toList();
                if (!allMappedKeywords.containsKey(wordInTitle)) {
                    allMappedKeywords.put(wordInTitle, new TreeSet<>());
                }
//               if(!articleWithKeywords.containsKey(articleUri)){
//                   articleWithKeywords.put(articleUri, new TreeSet<>());
//               }
//               var existingArticleKeyword = articleWithKeywords.get(articleUri);
                var existingKeyword = allMappedKeywords.get(wordInTitle);
                existingKeyword.add(articleUri);
//               existingKeyword.addAll(filteredWords);
//               existingArticleKeyword.addAll(filteredWords);
            }
        }
    }
}