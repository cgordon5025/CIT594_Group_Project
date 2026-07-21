package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.*;

import java.util.*;

/**
 * This class maintains the master map of Articles and the master map of titles to keywords
 */
public class ArticlesParsed {

    public static Map<String, Article> parsedArticles = new HashMap<>();
    public static TreeSet<Article> sortedArticles = new TreeSet();
}