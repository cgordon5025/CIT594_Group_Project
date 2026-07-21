package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.Article;

import java.util.*;

public class KeywordMap {
    //MAP W/ KEYWORD AS KEY AND VALUE IS A SET OF URIS, RELATING TO WHICH ARTICLES HAS SPEC. WORDS
    public static Map<String, Set<String>> allMappedKeywords = new HashMap<>();

    public static final Set<String> STOP_WORDS = TextFileReader.readTXTFile("stop_words.txt");

    //setting up for a trie built of fthe keywords
    public static class Node {
        public HashMap<Character, Node> children = new HashMap<>();
        public boolean endOfWord = false;

    }

    public static Node root = new Node();

    //recycled from HW 9
    public static void buildGraphFromArticles() {
        for (Article article : ArticlesParsed.parsedArticles.values()) {
            var articleTitle = article.getTitle();
            if (articleTitle.isEmpty()) continue;
            var titleNormalized = NormalizeText.normalizeText(Arrays.toString(articleTitle.split(" ")));

            var articleUri = article.getUri();
            for (String wordInTitle : titleNormalized) {
                if (STOP_WORDS.contains(wordInTitle) || wordInTitle.isEmpty() || wordInTitle.length() == 1) continue;
                if (!allMappedKeywords.containsKey(wordInTitle)) {
                    allMappedKeywords.put(wordInTitle, new TreeSet<>());
                }
                var existingKeyword = allMappedKeywords.get(wordInTitle);
                existingKeyword.add(articleUri);
            }
            //TODO: MY UNDERSTANDING IS THAT 'KEYWORDS' AR EALOS BUILT FORM THE BODY
//            var articleBody = article.getBody();
//            if(articleBody.isEmpty())continue;
//            var bodyNormalized = NormalizeText.normalizeText(Arrays.toString(articleBody.split(" ")));
//            for (String wordInBody : bodyNormalized) {
//                if (STOP_WORDS.contains(wordInBody) || wordInBody.isEmpty() || wordInBody.length() == 1) continue;
//                if (!allMappedKeywords.containsKey(wordInBody)) {
//                    allMappedKeywords.put(wordInBody, new TreeSet<>());
//                }
//                var existingKeyword = allMappedKeywords.get(wordInBody);
//                existingKeyword.add(articleUri);
//            }
        }
    }

    public static void insertWordToTrie(String word) {
        if (word == null || word.trim().isEmpty()) return; //ignore empty/null words
        String normalizedWord = word.trim().toLowerCase();
        Node parentNode = root;
        Node existingNode = null;

        for (int i = 0; i < normalizedWord.length(); i++) {
            char currChar = normalizedWord.charAt(i);
//            if (!Character.isLetter(currChar)) { //todo: this should not be filtering for nums/specical chars
//                continue; //skip numbers and non-alpha chars
//            }
            existingNode = (parentNode.children.containsKey(currChar)) ? parentNode.children.get(currChar) : null;
            if (existingNode == null) { //if its null we add it to the parent as a valid child
                parentNode.children.put(currChar, new Node());
                existingNode = parentNode.children.get(currChar);
                if (i == normalizedWord.length() - 1) {
                    existingNode.endOfWord = true;
                }
            } else {
                if (i == normalizedWord.length() - 1) {
                    existingNode.endOfWord = true; //word is a substring of diff so let's mark theree is an end here
                }
            }
            parentNode = existingNode;
        }
    }

    // this implementation is given to students in the starter code
    public static void insertListToTrie(String[] wordList) {
        for (String string : wordList) {
            if(string.length()<=1  )continue;
            insertWordToTrie(string);
        }
    }

    public static Node findEndOfPrefixNode(String prefix){
        if(prefix==null||prefix.trim().isEmpty()) return null;
        //prefix is pre-normalized
        Node parentNode = root;
        Node existingNode = null;
        for(int i=0;i<prefix.length();i++){
            existingNode = (parentNode.children.containsKey(prefix.charAt(i))) ? parentNode.children.get(prefix.charAt(i)) : null;
            if(existingNode==null){
                return null;
            }
            parentNode = existingNode;
        }
        return existingNode;
    }


}