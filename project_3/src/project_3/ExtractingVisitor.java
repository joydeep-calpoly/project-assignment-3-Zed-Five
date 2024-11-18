package project_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Scanner;
import java.util.logging.Level;

public class ExtractingVisitor implements Visitor {
	
	/**
	 * Sends ALL Simple Format inputs to the FileExtractor method.
	 * @Param i, a SimpleInput object passed from an accept() method.
	 * @return the VisitorOutputWrapper passed by the FileExtractorMethod.
	 */
	public VisitorOutputWrapper visit(SimpleInput i) throws FileNotFoundException {
		return FileExtractor(i);
	}
	
	/**
	 * Sends NewsAPI Format inputs to either the FileExtractor or URLExtractor method depending on the NewsAPIInput's enum type.
	 * @Param i, a NewsAPIInput object passed from an accept() method.
	 * @return the VisitorOutputWrapper passed by the FileExtractorMethod.
	 */
	public VisitorOutputWrapper visit(NewsAPIInput i) {
		
		//SWITCH BASED ON ENUM TYPES IN INPUT CLASS
		switch(i.getSourceType()) {
		
		//EXTRACTS FROM A URL
		case URL:
			return URLExtractor(i);
		//READS FILES
		case FILE:			
			try {
				return FileExtractor(i);
			}
			catch (FileNotFoundException e) {}
			
		//SHOULD NEVER BE USED
		default:
			throw new InvalidParameterException();
		}
		
	}
	
	/**
	 * Accesses the URL passed to it and makes API calls to extract current articles from the NewsAPI site
	 * Creates a single String of the results and wraps it in a VisitorOutputWrapper 
	 * @param i, the Input passed by one of the visit methods.
	 * @return a VisitorOutputWrapper containing the String extracted from the Source URL.
	 */
	private VisitorOutputWrapper URLExtractor (Input i) {
		StringBuilder urlbuilder = new StringBuilder();
        
        try {
            URL url = new URL(i.getSource());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String input;
                while ((input = in.readLine()) != null) {
                	urlbuilder.append(input);
                }
                in.close();
            } 
            else {
                System.err.println("GET request failed: " + responseCode);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
		
		System.out.println("Successful URL extraction!");
		return new VisitorOutputWrapper(urlbuilder.toString());
	}
	
	
	/**
	 * Reads the File Path passed to it
	 * Creates a single String from the file and wraps it in a VisitorOutputWrapper 
	 * @param i, the Input passed by one of the visit methods.
	 * @return a VisitorOutputWrapper containing the String extracted from the Source File.
	 */
	private VisitorOutputWrapper FileExtractor (Input i) throws FileNotFoundException {
		File inputfile = new File(i.getSource());
		StringBuilder filebuilder = new StringBuilder();
		Scanner scanner = new Scanner(inputfile);
					
		while (scanner.hasNextLine()) {
			filebuilder.append(scanner.nextLine());
		}
		scanner.close();
			
		String inputString = filebuilder.toString();
		
		System.out.println("Successful file extraction!");
		return new VisitorOutputWrapper(inputString);
	}
}
