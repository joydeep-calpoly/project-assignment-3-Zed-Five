package project_3;

import java.io.FileNotFoundException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public abstract class Input {
	protected String source;
	protected SourceType sourceType;
	
	String inputContent;
		
	enum SourceType {
		URL,
		FILE
	}
	
	/**
	 * Returns the source (whether a file path or a URL)
	 * @return source, a String
	 */
	String getSource() {
		return source;
	}
	
	/**
	 * Returns the input's format
	 * @return sourceType, a SourceType enum (either FILE or URL)
	 */
	SourceType getSourceType() {
		return sourceType;
	}
	
	/**
	 * Returns the inputContents, usually the result of a call to accept(ExtractingVisitor e)
	 * @return inputContent, a String
	 */
	String getInputContent() {
		return inputContent;
	}
	
	
	abstract void accept(ExtractingVisitor e) throws FileNotFoundException;
	abstract List<Article> accept(ParsingVisitor e) throws JsonMappingException, JsonProcessingException;
}
