package it.holls.writersutilityM;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.jsoup.Jsoup;

public class DocumentManipulator {

	public static XWPFDocument openFile(File docFile) throws FileNotFoundException {
		XWPFDocument doc = null;
		FileInputStream fis = null;
		if (!docFile.exists()) {
			throw new FileNotFoundException("The Word dcoument " + docFile.getName() + " does not exist.");
		}
		try {

			// Open the Word document file and instantiate the XWPFDocument
			// class.
			fis = new FileInputStream(docFile);
			doc = new XWPFDocument(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
					fis = null;
				} catch (IOException ioEx) {
					System.out.println("IOException caught trying to close " + "FileInputStream in the constructor of "
							+ "UpdateEmbeddedDoc.");
				}
			}
		}
		return doc;
	}

	public void readParagraphs(XWPFDocument document) {
		List<XWPFParagraph> paragraphs = document.getParagraphs();
		for (XWPFParagraph currentPar : paragraphs) {
			System.out.println("Testo analizzato: " + currentPar.getParagraphText());

			String[] splitted = currentPar.getParagraphText().split("[^\\p{L}\\p{Nd}]+");
			for (int i = 0; i < splitted.length; i++) {
				for (int j = i + 1; j < splitted.length; j++) {
					System.out.println("Distanza di Jaro tra " + splitted[i] + " e " + splitted[j] + ": "
							+ JaroDistance.apply(splitted[i], splitted[j]));
				}
			}
		}
	}
	
	public static String getParagraphText(XWPFDocument document) {
		String text = "";
		List<XWPFParagraph> paragraphs = document.getParagraphs();
		for (XWPFParagraph currentPar : paragraphs) {
			text+="\t"+currentPar.getParagraphText()+"\n";
		}
		return text;
	}

	public static String convertToHTML(FileInputStream in) throws XWPFConverterException, IOException {
		// convert .docx to HTML string
		XWPFDocument document = new XWPFDocument(in);

		XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(new File("word/media")));

		OutputStream out = new ByteArrayOutputStream();

		XHTMLConverter.getInstance().convert(document, out, options);
		String html = out.toString();
		
//System.out.println(html);
		return html;
	}

	public static Map<Integer, String> extractWords(String target, Integer minLength) {

		Pattern pattern = Pattern.compile("([A-Za-zÀ-ÿ]{"+minLength+",}(?:[A-Za-zÀ-ÿ]|(&#(?:19[2-9]|2[0-4][0-9]|25[0-5]);)))");
	
		Matcher matcher = pattern.matcher(target);
		
		Map<Integer, String> wordsAndPosition = new TreeMap<>();
		
		while (matcher.find()) {
			if(matcher.group().length()==0 || matcher.group(2)==null && matcher.group(1).length() < minLength
					|| matcher.group(2)!=null && (matcher.group(1).length()-matcher.group(2).length())+1 < minLength)
				continue;
    		wordsAndPosition.put(matcher.start(1), matcher.group(1));
		}

		return wordsAndPosition;
	}
	
	public static Map<Integer, String> extractWordsFromHTML(String paneText, Integer minLength) {
//System.out.println(paneText);
		
		Map<Integer, String> wordsAndPosition = new TreeMap<>();

		int currentLastIndex = 0;
		String text = paneText;
		int indexGt = -1, indexLt = 0;
		//Fare match sull'apertura del tag body e fare .end()
		boolean stop = false;
		while(true) {
			
			indexGt = text.indexOf(">",indexLt);
			if(indexGt==-1) {
				break;
			}
			
			if(paneText.substring(indexLt, indexGt+1).equals("<style>"))
				stop = true;
			
			indexLt = text.indexOf("<",indexGt);
			if(indexLt==-1) {
				break;
			}
			
			currentLastIndex = indexGt+1;

			//FIX-ME: soluzione provvisoria per escludere la ricerca dentro gli stili del css, racchusi da graffe - servirebbe una regex
			//<body.*?>(.*\/>)
			if(paneText.substring(currentLastIndex, indexLt).contains("{"))
				stop = true;
			
			if(!stop){
				String textFound = paneText.substring(currentLastIndex,indexLt);
				if(textFound.trim().length()==0){
					continue;
				}
	
	//System.out.println(textFound);
				
				Map<Integer, String> fragmentWordPosition = new TreeMap<>();
				fragmentWordPosition = extractWords(textFound, minLength);
				
				for (Map.Entry<Integer, String> entry : fragmentWordPosition.entrySet())
		    		wordsAndPosition.put(currentLastIndex + entry.getKey(), entry.getValue());
				currentLastIndex += textFound.length();
				
			}
			stop = false;
		}
wordsAndPosition.forEach((k,v)->System.out.println(v+" ("+k+")"));
		return wordsAndPosition;
	}

	public static String searchForSimilarities(String targetText, Map<Integer, String> map, Integer searchWindow,
			Double threshold) {
		Integer currentSearchWindow;

		if (searchWindow != 0)
			if (searchWindow > map.size())
				currentSearchWindow = map.size() - 1;
			else
				currentSearchWindow = searchWindow;
		else
			currentSearchWindow = map.size() - 1;

		ArrayList<Integer> sortedKeys = new ArrayList<Integer>(map.keySet());
		Collections.sort(sortedKeys);

		TreeMap<Integer,Double> indexToReplace = new TreeMap<>(Comparator.reverseOrder());
		for (int i = 0; i < map.size(); i++) {
			for (int j = i + 1; j - i < currentSearchWindow; j++) {
				if (j >= sortedKeys.size())
					break;
				Double distance = JaroDistance.apply(map.get(sortedKeys.get(i)), map.get(sortedKeys.get(j)));
				if (distance >= threshold) {
					System.out.println("Distanza di Jaro tra " + Jsoup.parse(map.get(sortedKeys.get(i))).text() + " e "
							+ Jsoup.parse(map.get(sortedKeys.get(j))).text() + ": " + distance);
					Double oldDistance = indexToReplace.get(sortedKeys.get(i));
					if(oldDistance==null || oldDistance!=null && oldDistance<distance)
						indexToReplace.put(sortedKeys.get(i),distance);
					oldDistance = indexToReplace.get(sortedKeys.get(j));
					if(oldDistance==null || oldDistance!=null && oldDistance<distance)
						indexToReplace.put(sortedKeys.get(j),distance);
				}
			}
		}
		
		StringBuffer sb = new StringBuffer(targetText);
		for (Entry<Integer,Double> entry : indexToReplace.entrySet()) {
			sb.replace(entry.getKey(), entry.getKey()+ map.get(entry.getKey()).length(),
					"<span style='background-color: "+getColor(entry.getValue(),threshold)+"'>" + map.get(entry.getKey()) + "</span>");
		}
		return sb.toString();
	}
	
	public static String getColor(double jaro, double minJaro) {
		if(jaro==1) return "#FF0000";
		double step = (1-minJaro)/5;
		if(jaro>1-step) return "#FF2B00";
		if(jaro>1-2*step) return "#FF5600";
		if(jaro>1-3*step) return "#FF8100";
		if(jaro>1-4*step) return "#FFAC00";
		return "#FFD700";
	}
	
}
