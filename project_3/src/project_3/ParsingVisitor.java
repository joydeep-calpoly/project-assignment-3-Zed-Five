package project_3;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project_3.Article;
import project_3.ArticlePackage;

public class ParsingVisitor implements Visitor {
	
	private final Logger logger;
	
	private ObjectMapper mapper;

	
	public ParsingVisitor (Logger logger, String log) throws SecurityException, IOException {
		this.logger = logger;
		
		FileHandler filehandler = new FileHandler(log);
		filehandler.setFormatter(new SimpleFormatter());
		
		logger.addHandler(filehandler);
		
		mapper = new ObjectMapper();
	}
	
	//FOR INJECTING DURING TESTING
    public ParsingVisitor(Logger logger, String log, ObjectMapper mapper) throws SecurityException, IOException {
		this.logger = logger;
		
		FileHandler filehandler = new FileHandler(log);
		filehandler.setFormatter(new SimpleFormatter());
		
		logger.addHandler(filehandler);
		
		this.mapper = mapper;
    }
	
		
		
	/**
	 * Takes a NewsAPIInput, gets its inputContent String, and uses JacksonDatabind to create Article objects from its content
	 * Adds those to a list to return, wraps it in a VisitorOutputWrapper, and returns it
	 * @param i, a NewsAPIInput
	 * @throws JsonMappingException, JsonProcessingException
	 * @returns a VisitorOutputWrapper containing an Article<List> of articles produced in this method
	 */
	@Override
	public VisitorOutputWrapper visit(NewsAPIInput i) throws JsonMappingException, JsonProcessingException {
		String in = i.getInputContent();
		ArrayList<Article> processedArticles = new ArrayList<Article>();
		ArticlePackage allArticles = mapper.readValue(in, ArticlePackage.class);
			for (Article a : allArticles.getUnwrappedArticles()) {
				try {
					validateArticle(a);
					processedArticles.add(a);
					System.out.print(a);
				}
				catch (IllegalArgumentException badArticle) {
					logger.log(Level.WARNING, "Invalid input detected: " + badArticle.getMessage());
				}
			}
		return new VisitorOutputWrapper(processedArticles);
	}

	
	
	/**
	 * Takes a SimpleInput, gets its inputContent String, and uses JacksonDatabind to create an Article object from its content
	 * Adds those to a list to return, wraps it in a VisitorOutputWrapper, and returns it
	 * @param i, a SimpleInput
	 * @throws JsonMappingException, JsonProcessingException
	 * @returns a VisitorOutputWrapper containing an Article<List> of articles produced in this method
	 */
	@Override
	public VisitorOutputWrapper visit(SimpleInput i) throws JsonMappingException, JsonProcessingException {
		String in = i.getInputContent();
		
		ArrayList<Article> processedArticles = new ArrayList<Article>();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"));

		Article a = mapper.readValue(in, Article.class);
		try {
			validateArticle(a);
			processedArticles.add(a);
			System.out.print(a);

		}
		catch (IllegalArgumentException badArticle) {
			logger.log(Level.WARNING, "Invalid input detected: " + badArticle.getMessage());
		}
		return new VisitorOutputWrapper(processedArticles);
	}
	
	/**
	 * Helper methods for checking that Articles are legitimate
	 * Checks that an Article has all four fields filled with non-null values, throwing an error if not
	 * 
	 * @param a an Article object
	 * @throws IllegalArgumentException
	 */
	private void validateArticle(Article a) throws IllegalArgumentException {
		if (a.getTitle() == null) {throw new IllegalArgumentException("Title field is null. Article rejected.");}
		if (a.getDescription() == null) {throw new IllegalArgumentException("Description field is null. Article rejected.");}
		if (a.getDate() == null) {throw new IllegalArgumentException("Date field is null. Article rejected.");}
		if (a.getUrl() == null) {throw new IllegalArgumentException("URL field is null. Article rejected.");}
	}
}
