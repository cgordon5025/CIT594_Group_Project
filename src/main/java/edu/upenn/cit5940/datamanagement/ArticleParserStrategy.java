package edu.upenn.cit5940.datamanagement;

import java.io.File;

public interface ArticleParserStrategy {
    /**
     * Parses the given file into a Map of Articles.
     *
     * @param file The input data file (CSV, JSON, etc.)
     * @throws Exception if parsing fails due to format errors or I/O issues.
     */
    void parse(File file) throws Exception;
}