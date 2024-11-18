package project_3;

import java.io.FileNotFoundException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SimpleInput extends Input{
	
	SimpleInput(String source) {
		this.source = source;
		sourceType = SourceType.FILE;
	}
	
	/**
	 * Sets inputContent to the unwrapped result of visit(this) called on an ExtractingVisitor injected into the method
	 * (This will turn the contents at the source into a String that can be parsed later)
	 */
	@Override
	void accept (ExtractingVisitor e) throws FileNotFoundException {
		inputContent = e.visit(this).returnExtractOutput();
	}
	
	/**
	 * Sends inputContent to an injected ParsingVisitor p
	 * @return the unwrapped contents of p.visit(this), which is a List<Article> generated b parsing inputContent  
	 */
	@Override
	List<Article> accept(ParsingVisitor e) throws JsonMappingException, JsonProcessingException {
		return e.visit(this).returnParseOutput();
	}
}
