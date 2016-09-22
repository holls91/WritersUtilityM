package it.holls.writersutilityM.documentReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocumentReaderPOI extends DocumentReader {

	@Override
	public String loadAndConvertToHTML(String inputFile) {
		XWPFDocument document;
		String html = "";
		try {
			document = new XWPFDocument(new FileInputStream(inputFile));
			XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(new File("word/media")));
			
			OutputStream out = new ByteArrayOutputStream();
			
			XHTMLConverter.getInstance().convert(document, out, options);
//			html = out.toString().replaceAll("font-family:'Times New Roman';", "");
			html = out.toString();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return html;
	}

}
