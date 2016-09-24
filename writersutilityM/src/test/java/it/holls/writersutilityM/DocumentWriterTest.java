package it.holls.writersutilityM;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import it.holls.writersutilityM.documentProcessor.DocumentProcessor;
import it.holls.writersutilityM.documentReader.DocumentReader;
import it.holls.writersutilityM.documentReader.FactoryDocumentReader;
import it.holls.writersutilityM.documentWriter.DocumentWriter;
import it.holls.writersutilityM.documentWriter.DocumentWriterDocx;
import it.holls.writersutilityM.iterator.HTMLWordIterator2;
import it.holls.writersutilityM.iterator.Word;
import it.holls.writersutilityM.iterator.fragment.HTMLFragmentIterator;
import it.holls.writersutilityM.utils.Utils;

public class DocumentWriterTest {
	
	@Test
	public void writeDocxFromXHTML(){
		String path = "resources/Kaden e le Fontane di Luce.docx";
		File file = new File(path);
		String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		DocumentReader documentReader = FactoryDocumentReader.getDocumentReader(extension);
		DocumentProcessor documentProcessor = new DocumentProcessor();
		DocumentWriter documentWriter = new DocumentWriterDocx();
		String text = Utils.fixHtml(documentReader.loadAndConvertToHTML(file.getPath()));
		text = text.replaceAll("width:\\d{1,}(?:[,\\.]\\d{1,})?pt", "width:90%");
		
		int minLength = 3;
		int window = 15;
		
		//Metodo 2
		Map<Integer, String> words2 = new TreeMap<>();
		HTMLFragmentIterator fi = new HTMLFragmentIterator(text);
		HTMLWordIterator2 wi = new HTMLWordIterator2(fi, minLength-1);
		while(wi.hasNext()) {
			Word word = wi.next();
			words2.put(word.getPosition(), word.getParola());
		}
		String newText2 = documentProcessor.searchForSimilarities(text, words2,
				window, Double.valueOf(85) / 100);
		
		documentWriter.writeFile(newText2, "resources/OUT_DOCX.docx");
		
	}
	
	@Test
	public void writeDocxFromHTML(){
		String path = "resources/Il momento sbagliato.odt";
		File file = new File(path);
		String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		DocumentReader documentReader = FactoryDocumentReader.getDocumentReader(extension);
		DocumentProcessor documentProcessor = new DocumentProcessor();
		DocumentWriter documentWriter = new DocumentWriterDocx();
		String text = Utils.fixHtml(documentReader.loadAndConvertToHTML(file.getPath()));
		text = text.replaceAll("width:\\d{1,}(?:[,\\.]\\d{1,})?pt", "width:90%");
		
		int minLength = 3;
		int window = 15;
		
		//Metodo 2
		Map<Integer, String> words2 = new TreeMap<>();
		HTMLFragmentIterator fi = new HTMLFragmentIterator(text);
		HTMLWordIterator2 wi = new HTMLWordIterator2(fi, minLength-1);
		while(wi.hasNext()) {
			Word word = wi.next();
			words2.put(word.getPosition(), word.getParola());
		}
		String newText2 = documentProcessor.searchForSimilarities(text, words2,
				window, Double.valueOf(85) / 100);
		
		documentWriter.writeFile(newText2, "resources/OUT_DOCX_HTML.docx");
		
	}
}
