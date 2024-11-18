package project_3;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "status", "totalResults" })

class ArticlePackage {
	private List<Article> unwrappedArticles;
	
	private ArticlePackage (@JsonProperty("articles") List<Article> unwrappedArticles) {
		this.unwrappedArticles = new ArrayList<>(unwrappedArticles);
	}
	
	
	/**
	 * Gets a list of every Article encapsulated by this package.
	 * @return an ArrayList of Articles contained by this article package.
	 */
	public List<Article> getUnwrappedArticles() {
		return new ArrayList<>(unwrappedArticles);
	}
}
