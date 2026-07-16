package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.Article;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParserStrategy implements ArticleParserStrategy{

    @Override
    public Map<String, Article> parse(File file) throws Exception {

        try (CharacterReader characterReader = new CharacterReader(file.getPath())) {
            return readAllArticles(characterReader);
        }
    }

    /**
     * Reads the entire JSON stream and parses it into a map of Articles.
     *
     * @return A map where the key is the article's URI (String) and the value
     * is the fully populated Article object.
     * @throws IOException when the underlying reader encounters an error.
     * @throws Exception when the CSV file is formatted incorrectly.
     */
    public Map<String, Article> readAllArticles(CharacterReader reader) throws IOException, Exception {

        Map<String, Article> articles = new HashMap<>();
        List<String> currentRecordFields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();

        // TODO: implement the JSON parser
        return new HashMap<String, Article>();
    }
}
