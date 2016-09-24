package it.holls.writersutilityM.documentWriter;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class DocumentWriterDocx extends DocumentWriter {

	@Override
	public void writeFile(String htmlSrc, String dest) {

		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
			XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);

			wordMLPackage.getMainDocumentPart().getContent().addAll(XHTMLImporter.convert(htmlSrc, null));

			wordMLPackage.save(new java.io.File(dest));
		} catch (Docx4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
