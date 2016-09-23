package it.holls.writersutilityM.documentReader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;

public class DocumentReaderDoc extends DocumentReader {

	@Override
	public String loadAndConvertToHTML(String inputFile) {
		  HWPFDocumentCore wordDocument = null;
		  String html = "";
		    WordToHtmlConverter wordToHtmlConverter;
			try {
				wordDocument = WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));
				wordToHtmlConverter = new WordToHtmlConverter(
				        DocumentBuilderFactory.newInstance().newDocumentBuilder()
				                .newDocument());
				wordToHtmlConverter.processDocument(wordDocument);
				org.w3c.dom.Document htmlDocument = wordToHtmlConverter.getDocument();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				DOMSource domSource = new DOMSource(htmlDocument);
				StreamResult streamResult = new StreamResult(out);
				
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer serializer = tf.newTransformer();
				serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				serializer.setOutputProperty(OutputKeys.INDENT, "yes");
				serializer.setOutputProperty(OutputKeys.METHOD, "html");
				serializer.transform(domSource, streamResult);
				//Replace necessario perché il tag META non è supportato dall'editorPane
//				html = new String(out.toByteArray()).replace("<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">", "");
				html = new String(out.toByteArray());
				out.close();
			} catch (ParserConfigurationException | TransformerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return html;

	}

}
