package edu.upenn.cit5940.datamanagement;

import java.io.File;

/**
 * Factory class responsible for determining and instantiating the
 * appropriate ArticleParserStrategy based on the provided file.
 */
public class ParserStrategyFactory {

    /**
     * Resolves and returns the correct ArticleParserStrategy for a given file.
     *
     * @param file The data file to parse.
     * @return A concrete implementation of ArticleParserStrategy (CSV or JSON).
     * @throws IllegalArgumentException if the file is null, does not exist, or is a directory.
     * @throws UnsupportedOperationException if the file format cannot be identified or supported.
     */
    public static ArticleParserStrategy getStrategy(File file) {

        // handle invalid inputs
        if (file == null) {
            throw new IllegalArgumentException("The file cannot be null.");
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("This file does not exist: " + file.getAbsolutePath());
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("This path is not a valid filepath: " + file.getAbsolutePath());
        }

        String fileName = file.getName().toLowerCase();

        // file extension check
        if (fileName.endsWith(".csv")) {
            return new CSVParserStrategy();
        }
        if (fileName.endsWith(".json")) {
            return new JSONParserStrategy();
        }

        // if we fail to identify the correct file type
        throw new UnsupportedOperationException(
                "The application could not determine a parsing strategy " +
                        "for file: '" + file.getName() + "'. Please provide a valid CSV or JSON file."
        );
    }
}
