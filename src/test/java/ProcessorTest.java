import java.io.File;
import java.util.*;
import java.util.stream.Stream;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.common.dto.TopTopicInfo;
import edu.upenn.cit5940.datamanagement.ArticleParserStrategy;
import edu.upenn.cit5940.datamanagement.ArticlesParsed;
import edu.upenn.cit5940.datamanagement.KeywordMap;
import edu.upenn.cit5940.datamanagement.ParserStrategyFactory;
import edu.upenn.cit5940.processor.ArticleProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ProcessorTest {
    private static ArticleProcessor processor = new ArticleProcessor();

    @BeforeEach
    public void beforeAll() throws Exception {
        File dataFile = new File("articles_small.json");
        ArticleParserStrategy parser = ParserStrategyFactory.getStrategy(dataFile);
        parser.parse(dataFile);
        KeywordMap.buildGraphFromArticles();
        KeywordMap.insertListToTrie(ArticlesParsed.parsedArticles.values().stream().map(Article::getTitle).toArray(String[]::new));

        // DO NOT remove this method
        // This method is run once before all the test methods in this class
        // You can use this method to set up any common data needed for the tests
    }

    @Test
    void testSearchArticlesByKeywords() {
        // TODO: Write at least 5 test cases with assert statements. All cases must pass
        List<String> expectedTitlesSingleSearch = new ArrayList<>(List.of("Airbnb's Software Patent Rates Your Psychopathy Based On Your Social Media Activity"));
        assertEquals(expectedTitlesSingleSearch, processor.searchArticlesByKeywords(List.of("airbnb")));
        List<String> expectedTitlesMultiwordSearch = new ArrayList<>(List.of("Twitter board vows legal action after Musk drops bid", "Elon Musk could block contact with aliens if they use Twitter, expert warns"));

        //TEST 2: 2+ WORD SEARCH
        assertEquals(expectedTitlesMultiwordSearch, processor.searchArticlesByKeywords(List.of("twitter", "and", "musk")));
        //TEST 3: empty search PARAMS

        //TEST 4: NULL SEARCH PARAMS

        //TEST 5: GIBBERISH/ NONEXISTENT WORD SEARCH


        //
    }

    @Test
    void testGetAutocompleteSuggestions() {
        //"search sometinhg"

//        fail("not yet implemented");
    }

    @Test
    void testCalculateTopTopics() {
        ArrayList<TopTopicInfo> expectedJanTopics = new ArrayList<>(List.of(new TopTopicInfo("activity", 1),
                new TopTopicInfo("airbnb", 1),
                new TopTopicInfo("announces", 1),
                new TopTopicInfo("avoids", 1),
                new TopTopicInfo("detailed", 1),
                new TopTopicInfo("engine", 1),
                new TopTopicInfo("featured", 1),
                new TopTopicInfo("intel", 1),
                new TopTopicInfo("land", 1),
                new TopTopicInfo("media", 1)));
        assertIterableEquals(
                expectedJanTopics.stream()
                        .map(t -> t.getTopicName() + ":" + t.getMentionCount())
                        .toList(),
                processor.calculateTopTopics("2020-01").stream()
                        .map(t -> t.getTopicName() + ":" + t.getMentionCount())
                        .toList()
        );//        Map<String, Integer> expectedJanTopics = Stream.of(new Object[][]{
//                {"your",2},{"activity",1},{"announces",1},{"detailed",1},{"engine",1},
//                {"based",1},{""}
//        });


//        fail("not yet implemented");
    }

    @Test
    void testCalculateTrends() {
//        fail("not yet implemented");
    }

    @Test
    void testGetArticlesByDateRange() {

//        fail("not yet implemented");
    }

    @Test
    void testGetArticleById() {
//        fail("not yet implemented");
    }

    @Test
    void testGetArticleDetails() {
//        fail("not yet implemented");
    }

    @Test
    void testGetTotalArticleCount() {
//        fail("not yet implemented");
    }

}