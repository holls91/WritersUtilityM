package it.holls.writersutilityM.documentReader;

import java.io.OutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class DocumentReaderDocx4j extends DocumentReader {

	@Override
	public String loadAndConvertToHTML(String inputFile) {
		try {
			WordprocessingMLPackage wordMLPackage = Docx4J.load(new java.io.File(inputFile));
			HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
	    	htmlSettings.setWmlPackage(wordMLPackage);
	    		    	
	    	OutputStream os = new ByteArrayOutputStream();
	    	
	    	Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);
	    	Docx4jProperties.setProperty(
	    		    "org.docx4j.convert.out.common.writer.AbstractMessageWriter", false);
	    	Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
	    	
	    	return new String(os.toString());
		} catch (Docx4JException e) {
			e.printStackTrace();
			return null;
		}

	}

}
