package project_3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Tester {
	
	@Mock
    private NewsAPIInput mockNewsAPIInput;
	
	@Mock
    private SimpleInput mockSimpleInput;
	
	@Mock
    private ObjectMapper mockMapper;
	
	@Mock
    private ArticlePackage mockArticlePackage;
	
	@Mock
    private Article mockArticle;
	
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
    /**
     * Tests if the system will call the NewsAPI branch of the ParsingVisitor when prompted with a NewsAPI JSON
     * @throws SecurityException
     * @throws IOException
     * @throws ParseException
     */
    
	@Test
	public void testParserNewssAPI() throws SecurityException, IOException, ParseException {
		Logger errorLogger = Logger.getLogger("ArticleFormatErrorLogger");
		ParsingVisitor parser = new ParsingVisitor(errorLogger, "logs/logout.txt");
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    Date d = dateFormat.parse("1999-12-31T16:00:00Z"); //DATES REQUIRE SPECIAL CONFIGURATIONS TO TEST
		
		String testJSON = "{\"status\":\"ok\",\"totalResults\":999,\"articles\":[{\"source\":{\"id\":\"test\",\"name\":\"TEST\"},\"author\":\"ByJohnSmith\",\"title\":\"TEST\",\"description\":\"This is a test of the System.\",\"url\":\"https://www.test.com\",\"urlToImage\":\"https://test.jpg\",\"publishedAt\":\"2000-01-01T00:00:00Z\",\"content\":\"LoremIpsum\"}]}";
		
		//SENDS MOCKS INSTEAD OF ACTUAL OBJECTS AS INTERMEDIARIES
		when(mockNewsAPIInput.getInputContent()).thenReturn(testJSON);
        when(mockMapper.readValue(testJSON, ArticlePackage.class)).thenReturn(mockArticlePackage);
        when(mockArticlePackage.getUnwrappedArticles()).thenReturn(new ArrayList<Article>() {{add(mockArticle);}});

        //MOCKS THE ARTICLE
        when(mockArticle.getTitle()).thenReturn("TEST");
        when(mockArticle.getDescription()).thenReturn("This is a test of the System.");
        when(mockArticle.getDate()).thenReturn(d);
        when(mockArticle.getUrl()).thenReturn("https://www.test.com\\");
        
        VisitorOutputWrapper result = parser.visit(mockNewsAPIInput);
        
        assertNotNull(result);
        assertEquals(1, result.returnParseOutput().size());

	}
	
	
    /**
     * Tests if the system will call the NewsAPI branch of the ParsingVisitor when prompted with a NewsAPI JSON
     * @throws SecurityException
     * @throws IOException
     * @throws ParseException
     */
	@Test
	public void testParserSimple() throws SecurityException, IOException, ParseException {
		Logger errorLogger = Logger.getLogger("ArticleFormatErrorLogger");
		ParsingVisitor parser = new ParsingVisitor(errorLogger, "logs/logout.txt");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    Date d = dateFormat.parse("2000-01-01T00:00:00Z"); //DATES REQUIRE SPECIAL CONFIGURATIONS TO TEST
		
		String testJSON = "{\r\n" + "  \"description\": \"This is meant to test Simple.\",\r\n" + "  \"publishedAt\": \"2000-01-01 00:00:00.000000\",\r\n" + "  \"title\": \"TEST\",\r\n" + "  \"url\": \"https://www.test.com\"\r\n" + "}";
		
		//SENDS MOCKS INSTEAD OF ACTUAL OBJECTS AS INTERMEDIARIES
		when(mockSimpleInput.getInputContent()).thenReturn(testJSON);
        when(mockMapper.readValue(testJSON, ArticlePackage.class)).thenReturn(mockArticlePackage);
        when(mockArticlePackage.getUnwrappedArticles()).thenReturn(new ArrayList<Article>() {{add(mockArticle);}});

        //MOCKS THE ARTICLE
        when(mockArticle.getTitle()).thenReturn("TEST");
        when(mockArticle.getDescription()).thenReturn("This is meant to test Simple.");
        when(mockArticle.getDate()).thenReturn(d);
        when(mockArticle.getUrl()).thenReturn("https://www.test.com\\");
        
        VisitorOutputWrapper result = parser.visit(mockSimpleInput);
        
        assertNotNull(result);
        assertEquals(1, result.returnParseOutput().size());
	}
}
