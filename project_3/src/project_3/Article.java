package project_3;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties({ "source", "author", "urlToImage", "content" })

class Article {
	private String title;
	private String description;
	private Date published_date_time =  new Date();
	private String url;
	
	@JsonCreator
	private Article (@JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("publishedAt") Date published_date_time, @JsonProperty("url") String url) {
		this.title = title;
		this.description = description;
		if (published_date_time != null) {this.published_date_time = new Date(published_date_time.getTime());}
		else {this.published_date_time = null;}
		this.url = url;
	}
	
	
	/**
	 * Gets the title of the Article object.
	 * @return a string containing the article's title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the description of the Article object.
	 * @return a string containing the article's description.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gets the date of the Article object.
	 * @return a new Date object (defensive copy) containing the article's date (or null if necessary).
	 */
	public Date getDate() {
		if (published_date_time != null) {return new Date(published_date_time.getTime());}
		else {return null;}
	}
	
	
	/**
	 * Gets the URL of the Article object.
	 * @return a string containing the article's URL.
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Overrides equals() to compare the four content fields of two Articles. If each fields is identical to its counterpart, return true.
	 * @return true if all four fields are equal to one another, false otherwise
	 */
	
	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {return false;}
		Article a = (Article) o;
		return ((this.getTitle().equals(a.getTitle())) 
				&& (this.getDescription().equals(a.getDescription()))
				&& (this.getDate().equals(a.getDate()))
				&& (this.getUrl().equals(a.getUrl())));
	}
	
	/**
	 * Overrides toString() to return the contents of each of the four fields with labels and space breaks
	 * @return true if all four fields are equal to one another, false otherwise
	 */
	
	@Override
	public String toString() {
		return ("Title: " + title + "\n" 
				+ "Description: " + description + "\n" 
				+ "Date: " + published_date_time + "\n" 
				+"URL: " + url + "\n" + "\n");
	}
}
