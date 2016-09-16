package it.holls.writersutilityM;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import documentProcessor.DocumentProcessor;

public class DocumentProcessorTest {

	@Test
	public void searchForWrongWordsTest() {
		String sampleText = "ciao perch&#232; perche'";
		String transformedText = DocumentProcessor.searchForWrongWords(sampleText);
		assertEquals("ciao <span style='background-color: #FF0000'>perch&#233;</span> <span style='background-color: #FF0000'>perch&#233;</span>", transformedText);
	}

}
