package it.holls.writersutilityM;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
        	System.out.println("Starting app...");
        	DocumentManipulator dm = new DocumentManipulator();
        	String inputFile = "C:/Users/Gattino/Documents/SampleDocx.docx";
        	XWPFDocument document = dm.openFile(new File(inputFile));
			System.out.println("Document loaded");
			dm.readParagraphs(document);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
