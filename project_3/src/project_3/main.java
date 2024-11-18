package project_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import project_3.Input.SourceType;


public class main {
	
    public static void main (String[] args) throws SecurityException, IOException {
		Logger errorLogger = Logger.getLogger("ArticleFormatErrorLogger");

		//THREE TEST CASES
		Input inputNewsAPIFromFile = new NewsAPIInput("inputs/newsapi.txt", SourceType.FILE);
		Input inputNewsAPIFromURL = new NewsAPIInput("https://newsapi.org/v2/top-headlines?country=us&apiKey=f41ea715f9404d669c1cb285cfd726e0", SourceType.URL);
		Input inputSimpleFromFile = new SimpleInput("inputs/simple.txt");
		List<Input> inputs= new ArrayList<>();
		inputs.add(inputNewsAPIFromFile);
		inputs.add(inputNewsAPIFromURL);
		inputs.add(inputSimpleFromFile);
		
		//EXTRACTING VIA THEIR RESPECTIVE METHODS
		ExtractingVisitor extractor = new ExtractingVisitor();
		for (Input i : inputs) {
			i.accept(extractor);
		}
		
		//PARSING VIA THEIR RESPECTIVE METHODS
		List<Article> listOfParsedArticles = new ArrayList<>();
		ParsingVisitor parser = new ParsingVisitor(errorLogger, "logs/logout.txt");
		for (Input i : inputs) {
			listOfParsedArticles.addAll(i.accept(parser));
		}
		
		for (Article a : listOfParsedArticles) {
			System.out.println(a.toString());
		}
    }
}
