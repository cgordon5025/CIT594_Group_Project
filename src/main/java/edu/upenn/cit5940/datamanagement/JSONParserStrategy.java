package edu.upenn.cit5940.datamanagement;

import com.fasterxml.jackson.databind.JsonNode;
import edu.upenn.cit5940.common.dto.Article;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParserStrategy implements ArticleParserStrategy {

//    public class JSONParserStrategy {
//    @Override
    public void parse(File file) throws Exception {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            var jsonDeserialized = objectMapper.readTree(file);

//            byte[] jsonFile = Files.readAllBytes(file.toPath());
            readAllArticles(jsonDeserialized);
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        try (CharacterReader characterReader = new CharacterReader(file.getPath())) {
//            return readAllArticles(characterReader);
//        }
    }

    /**
     * Reads the entire JSON stream and adds it to the growing map of Articles stored in ArticlesParsed
     *
     *
     * is the fully populated Article object.
     * @throws IOException when the underlying reader encounters an error.
     * @throws Exception when the CSV file is formatted incorrectly.
     */
    public void readAllArticles(JsonNode jsonDeserialized) throws IOException, Exception {
//        var jsonFile = Files.readAllBytes(Paths.get(fileName));
//        var jsonDeserialized = objectMapper.readTree(jsonFile);
        Map<String, Article> articles = new HashMap<>();
        List<String> currentRecordFields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();

        // TODO: implement the JSON parser
//        return new HashMap<String, Article>();
    }
}