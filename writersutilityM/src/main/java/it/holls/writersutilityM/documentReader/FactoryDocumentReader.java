package it.holls.writersutilityM.documentReader;

import it.holls.writersutilityM.ui.GUI2;

public class FactoryDocumentReader {

	public static DocumentReader getDocumentReader(String format) {
		switch (format) {
		case "docx":
			if(GUI2.menuSelection.equals("POI"))
				return new DocumentReaderPOI();
			else
				return new DocumentReaderDocx4j();
		case "doc":
			return new DocumentReaderDoc();
		case "odt":
			return new DocumentReaderOdt();
		case "txt":
			return new DocumentReaderPlainText();
		case "rtf":
			return new DocumentReaderRtf();
		default:
			return null;
		}
	}
}
