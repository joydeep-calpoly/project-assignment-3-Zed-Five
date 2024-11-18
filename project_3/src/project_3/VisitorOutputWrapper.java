package project_3;

import java.util.List;

public class VisitorOutputWrapper {
	private List<Article> parseOutput = null;
	private String extractOutput = null;
	
	VisitorOutputWrapper (List<Article> parseOutput) {
		this.parseOutput = parseOutput;
	}
	
	VisitorOutputWrapper (String extractOutput) {
		this.extractOutput = extractOutput;
	}
	
	/**
	 * Returns the parseOutput if one exists (i.e. if this object was instantiated by a ParsingVisitor)
	 * @throws a NullPointerException if this wrapper isn't holding a parseOutput
	 * @return parseOutput, a List<Article>
	 */
	public List<Article> returnParseOutput() {
		if (parseOutput != null) {
			return parseOutput;
		}
		else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * Returns the extractOutput if one exists (i.e. if this object was instantiated by an ExtractingVisitor)
	 * @throws a NullPointerException if this wrapper isn't holding an extractOutput
	 * @return extractOutput, a String
	 */
	public String returnExtractOutput() {
		if (extractOutput != null) {
			return extractOutput;
		}
		else {
			throw new NullPointerException();
		}
	}
}
