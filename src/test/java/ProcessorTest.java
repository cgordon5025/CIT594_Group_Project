import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.datamanagement.ArticleParserStrategy;
import edu.upenn.cit5940.datamanagement.ArticlesParsed;
import edu.upenn.cit5940.datamanagement.KeywordMap;
import edu.upenn.cit5940.datamanagement.ParserStrategyFactory;
import edu.upenn.cit5940.processor.ArticleProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;


public class ProcessorTest {
    private static ArticleProcessor processor = new ArticleProcessor();
    @BeforeEach
    public void beforeAll() throws Exception{
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
        List<String> expectedTitlesMultiwordSearch = new ArrayList<>(List.of());

        //TEST 2: 2+ WORD SEARCH
        //TEST 3: empty search PARAMS

        //TEST 4: NULL SEARCH PARAMS

        //TEST 5: GIBBERISH/ NONEXISTENT WORD SEARCH


        //
    }
    @Test
    void testGetAutocompleteSuggestions(){
        fail("not yet implemented");
    }
    @Test
    void testCalculateTopTopics(){
        fail("not yet implemented");
    }

    @Test
    void testCalculateTrends(){
        fail("not yet implemented");
    }

    @Test
    void testGetArticlesByDateRange(){
        fail("not yet implemented");
    }

    @Test
    void testGetArticleById(){
        fail("not yet implemented");
    }

    @Test
    void testGetArticleDetails(){
        fail("not yet implemented");
    }

    @Test
    void testGetTotalArticleCount(){
        fail("not yet implemented");
    }

}