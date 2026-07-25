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
import edu.upenn.cit5940.logging.Logger;

public class JSONParserStrategy implements ArticleParserStrategy {

    @Override
    public void parse(File file) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var jsonDeserialized = objectMapper.readTree(file);
            readAllArticles(jsonDeserialized);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Logger logger = Logger.getInstance();

    /**
     * Reads the entire JSON stream and parses it into a map of Articles.
     *
     * @return A map where the key is the article's URI (String) and the value
     * is the fully populated Article object.
     * @throws IOException when the underlying reader encounters an error.
     * @throws Exception   when the CSV file is formatted incorrectly.
     */
    public void readAllArticles(JsonNode jsonDeserialized) {
        int lineNum = 1;

        try {
            List<String> currentRecordFields = new ArrayList<>();
            for (JsonNode jsonRecord : jsonDeserialized) {
                Iterator<Map.Entry<String, JsonNode>> jsonRecordFields = jsonRecord.fields();
                while (jsonRecordFields.hasNext()) {
                    Map.Entry<String, JsonNode> field = jsonRecordFields.next();
                    JsonNode fieldValue = field.getValue();
                    currentRecordFields.add(fieldValue.asText());
                    lineNum++;
                }
                ProcessArticleRecord.processRecord(currentRecordFields);
                currentRecordFields.clear();
            }
        } catch (Exception e) {
            logger.LogInformation(String.format("Failed to parse record entry <%d>", lineNum), Logger.LogStatus.ERROR);
        }
    }
}