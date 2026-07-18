package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.Article;
import java.io.File;
import java.util.Map;

public interface ArticleParserStrategy {
    /**
     * Parses the given file into a Map of Articles.
     *
     * @param file The input data file (CSV, JSON, etc.)
     * @return A map where the key is the article's URI and the value is the Article object.
     * @throws Exception if parsing fails due to format errors or I/O issues.
     */
    void parse(File file) throws Exception;
}