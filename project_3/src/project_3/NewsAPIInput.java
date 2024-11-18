package project_3;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class NewsAPIInput extends Input{
	
	NewsAPIInput(String source, SourceType sourceType) {
		this.source = source;
		this.sourceType = sourceType;
	}
	
	/**
	 * Sets inputContent to the unwrapped result of visit(this) called on ExtractingVisitor e injected into the method
	 * (This will turn the contents at the source into a String that can be parsed later)
	 */
	@Override
	void accept (ExtractingVisitor e) {
		inputContent = e.visit(this).returnExtractOutput();
	}
	
	/**
	 * Sends inputContent to an injected ParsingVisitor p
	 * @return the unwrapped contents of p.visit(this), which is a List<Article> generated b parsing inputContent  
	 */
	@Override
	List<Article> accept(ParsingVisitor p) throws JsonMappingException, JsonProcessingException {
		return p.visit(this).returnParseOutput();
	}
}
