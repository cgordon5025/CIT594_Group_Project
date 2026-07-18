package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.Article;

import java.util.*;

public class KeywordMap {
    //MAP W/ KEYWORD AS KEY AND VALUE IS A SET OF URIS, RELATING TO WHICH ARTICLES HAS SPEC. WORDS
    public static Map<String, Set<String>> allMappedKeywords = new HashMap<>();

    public static final Set<String> STOP_WORDS = TextFileReader.readTXTFile("stop_words.txt");
    //    RECYCLED FROM HW 6

    //recycled from HW 9
    public static void buildGraphFromArticles() {
        for (Article article : ArticlesParsed.parsedArticles.values()) {
            var articleTitle = article.getTitle();
            if (articleTitle.isEmpty()) continue;
            var titleNormalized = NormalizeText.normalizeText(Arrays.toString(articleTitle.split(" ")));

            var articleUri = article.getUri();
            for (String wordInTitle : titleNormalized) {
                if (STOP_WORDS.contains(wordInTitle) || wordInTitle.isEmpty()|| wordInTitle.length()==1) continue;
                if (!allMappedKeywords.containsKey(wordInTitle)) {
                    allMappedKeywords.put(wordInTitle, new TreeSet<>());
                }                var existingKeyword = allMappedKeywords.get(wordInTitle);
                existingKeyword.add(articleUri);
            }
        }
    }
}