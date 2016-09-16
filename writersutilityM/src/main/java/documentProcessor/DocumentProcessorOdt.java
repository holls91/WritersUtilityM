package documentProcessor;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.converter.odt.odfdom.xhtml.ODF2XHTMLConverter;

public class DocumentProcessorOdt extends DocumentProcessor {

	@Override
	public String loadAndConvertToHTML(String inputFile) {
		String html = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Options options = Options.getTo(ConverterTypeTo.XHTML).via(ConverterTypeVia.ODFDOM);
			ODF2XHTMLConverter.getInstance().convert(new FileInputStream(inputFile), out, options);
//			html = out.toString().replace("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">", "").replace("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />", "");
			html = new String(out.toByteArray());
			out.close();
		} catch (XDocConverterException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html;
	}

}
