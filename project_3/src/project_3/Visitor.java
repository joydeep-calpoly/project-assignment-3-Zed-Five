package project_3;

import java.io.FileNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface Visitor {
	VisitorOutputWrapper visit (NewsAPIInput i) throws JsonMappingException, JsonProcessingException;
	VisitorOutputWrapper visit (SimpleInput i) throws FileNotFoundException, JsonMappingException, JsonProcessingException;
}
