package edu.upenn.cit5940.datamanagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.upenn.cit5940.common.dto.Article;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParserStrategy implements ArticleParserStrategy {

//    public class JSONParserStrategy {
//    @Override
    public void parse(File file) throws Exception {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            var jsonDeserialized = objectMapper.readTree(file);
            readAllArticles(jsonDeserialized);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
    public void readAllArticles(JsonNode jsonDeserialized) throws IOException, Exception {

        List<String> currentRecordFields = new ArrayList<>();
        for(JsonNode jsonRecord: jsonDeserialized){
            Iterator<Map.Entry<String, JsonNode>> jsonRecordFields = jsonRecord.fields();
            while (jsonRecordFields.hasNext()) {
                Map.Entry<String, JsonNode> field = jsonRecordFields.next();
                JsonNode fieldValue = field.getValue();
                currentRecordFields.add(fieldValue.asText());
            }
            ProcessArticleRecord.processRecord(currentRecordFields);
            currentRecordFields.clear();
        }
    }
}