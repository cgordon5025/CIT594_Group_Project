package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.*;

import java.util.*;

/**
 * This class maintains the master map of Articles and the master map of titles to keywords
 */
public class ArticlesParsed {
    public static class ArticleDate implements Comparable<ArticleDate>{
        String publishDate;
        String articleTitle;
        public ArticleDate(String publishDate, String articleTitle){
            this.publishDate = publishDate;
            this.articleTitle = articleTitle;
        }
        public int compareTo(ArticleDate other) {
            // Sort by first value, then by second value
            return this.publishDate.compareTo(other.publishDate);
        }
    }



    public static Map<String, Article> parsedArticles = new HashMap<>();
//    public static TreeSet<ArticleDate> articlesPubDates = new TreeSet(); //by default its ascending order
    public static TreeSet<Article> sortedArticles = new TreeSet();

}