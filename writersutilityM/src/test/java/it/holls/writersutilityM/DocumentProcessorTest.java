package it.holls.writersutilityM;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.Test;

import documentProcessor.DocumentProcessor;
import documentProcessor.DocumentProcessorInLineText;
import documentProcessor.FactoryDocumentReader;
import iterator.HTMLWordIterator2;
import iterator.Word;
import iterator.fragment.HTMLFragmentIterator;
import utils.Utils;

public class DocumentProcessorTest {

//	private static String sampleText = "ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche' ciao perch&#232; perche'";
	private static String sampleText = "s&#242; puzzi qual&#8217;&#232; prr";
//	private static String expectedTransformedText = "ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span> ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span>";
	private static String expectedTransformedText = "<span style='background-color: #FF0000'>so</span> puzzi <span style='background-color: #FF0000'>qual &#232;</span> prr";
	
	@Test
	public void searchForWrongWordsRegexTest() {
		DocumentProcessor dp = new DocumentProcessorInLineText();
		String transformedText = "";
		for(int i=0; i<1000; i++)
			transformedText = dp.searchForWrongWords(sampleText);
		assertEquals(expectedTransformedText, transformedText);
	}
	
	@Test
	public void searchForWrongWordsReplaceTest() {
		DocumentProcessor dp = new DocumentProcessorInLineText();
		String transformedText = "";
		for(int i=0; i<1000; i++)
			transformedText = dp.searchForWrongWordsReplaceAll(sampleText);
		assertEquals(expectedTransformedText, transformedText);
	}

	@Test
	public void searchForAllWrongWordsReplaceTest() {
		DocumentProcessor dp = new DocumentProcessorInLineText();
		String sampleText = utils.Utils.itWrongWords.entrySet().stream().map(Entry::getKey).collect(Collectors.joining(" "));
		String transformedText = dp.searchForWrongWordsReplaceAll(sampleText);
		assertEquals(utils.Utils.itWrongWords.entrySet().stream().map(Entry::getValue).collect(Collectors.joining("</span> <span style='background-color: #FF0000'>", "<span style='background-color: #FF0000'>", "</span>")), transformedText);
	}
	
	@Test
	public void compareWordExtractingMethods(){
		String path = "resources/Cap1 - Il testimone_NATHAN_OK.docx";
		File file = new File(path);
		String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		DocumentProcessor documentReader = FactoryDocumentReader.getDocumentReader(extension);
		String text = Utils.fixHtml(documentReader.loadAndConvertToHTML(file.getPath()));
		text = text.replaceAll("width:\\d{1,}(?:[,\\.]\\d{1,})?pt", "width:90%");
		
		int minLength = 4;
		
		//Metodo 1
		Map<Integer, String> words = documentReader.extractWordsFromHTML(text,minLength);
		String newText = documentReader.searchForSimilarities(text, words,
				15, Double.valueOf(85) / 100);
		
		//Metodo 2
		Map<Integer, String> words2 = new TreeMap<>();
		HTMLFragmentIterator fi = new HTMLFragmentIterator(text);
		HTMLWordIterator2 wi = new HTMLWordIterator2(fi, minLength-1);
		while(wi.hasNext()) {
			Word word = wi.next();
			words2.put(word.getPosition(), word.getParola());
		}
		String newText2 = documentReader.searchForSimilarities(text, words2,
				15, Double.valueOf(85) / 100);
		
		//Check
		assertEquals(newText, newText2);
	}
}
