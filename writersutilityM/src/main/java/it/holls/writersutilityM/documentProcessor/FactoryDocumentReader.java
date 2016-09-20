package it.holls.writersutilityM.documentProcessor;

public class FactoryDocumentReader {

	public static DocumentProcessor getDocumentReader(String format) {
		switch (format) {
		case "docx":
			return new DocumentProcessorDocx();
		case "doc":
			return new DocumentProcessorDoc();
		case "odt":
			return new DocumentProcessorOdt();
		case "txt":
			return new DocumentProcessorPlainText();
		case "rtf":
			return new DocumentProcessorRtf();
		default:
			return null;
		}
	}
}
