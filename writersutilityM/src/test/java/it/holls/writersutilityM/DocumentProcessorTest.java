package it.holls.writersutilityM;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;

import documentProcessor.DocumentProcessor;
import documentProcessor.DocumentProcessorInLineText;
import documentProcessor.FactoryDocumentReader;
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
		String path = "resources/SampleDocx.docx";
		File file = new File(path);
		String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		DocumentProcessor documentReader = FactoryDocumentReader.getDocumentReader(extension);
		String text = Utils.fixHtml(documentReader.loadAndConvertToHTML(file.getPath()));
		text = text.replaceAll("width:\\d{1,}(?:[,\\.]\\d{1,})?pt", "width:90%");
		
		//Metodo 1
		Map<Integer, String> words = documentReader.extractWordsFromHTML(text,3);
		String newText = documentReader.searchForSimilarities(text, words,
				15, Double.valueOf(85) / 100);
	}
}
