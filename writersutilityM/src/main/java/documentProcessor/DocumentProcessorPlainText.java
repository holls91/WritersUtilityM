package documentProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import utils.Utils;

public class DocumentProcessorPlainText extends DocumentProcessor {

	@Override
	public String loadAndConvertToHTML(String inputFile) {
		StringBuilder builder = new StringBuilder();
		try {
			
			String inputText = new String(Files.readAllBytes(Paths.get(inputFile)), "ISO-8859-1");
			return Utils.createHtmlFromText(inputText);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}

}
