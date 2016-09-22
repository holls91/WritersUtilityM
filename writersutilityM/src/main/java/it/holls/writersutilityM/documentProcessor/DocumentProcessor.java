package it.holls.writersutilityM.documentProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import it.holls.writersutilityM.DocumentManipulator;
import it.holls.writersutilityM.JaroDistance;
import it.holls.writersutilityM.documentReader.DocumentReader;
import it.holls.writersutilityM.ui.UIUtility;

public abstract class DocumentProcessor {
	
	protected DocumentReader documentReader;

	public String loadAndConvertToHTML(String inputFile, DocumentReader documentReader){
		return documentReader.loadAndConvertToHTML(inputFile);
	}
	
	public abstract String loadAndConvertToHTML(String inputFile);

	public Map<Integer, String> extractWordsFromHTML(String paneText, Integer minLength) {
		// System.out.println(paneText);
		int indexGt = -1, indexLt = 0;

		String bodyRegex = "<body.*?>([\\s\\S]*)</.*?>";
		Pattern pattern = Pattern.compile(bodyRegex);
		Matcher matcher = pattern.matcher(paneText);
		if(matcher.find()){
			indexLt = matcher.start();
		}
		Map<Integer, String> wordsAndPosition = new TreeMap<>();

		int currentLastIndex = 0;
		String text = paneText;
		// Fare match sull'apertura del tag body e fare .end()
		boolean stop = false;
		while (true) {

			indexGt = text.indexOf(">", indexLt);
			if (indexGt == -1) {
				break;
			}

			if (paneText.substring(indexLt, indexGt + 1).equals("<style>"))
				stop = true;

			indexLt = text.indexOf("<", indexGt);
			if (indexLt == -1) {
				break;
			}

			currentLastIndex = indexGt + 1;

			// FIX-ME: soluzione provvisoria per escludere la ricerca dentro gli
			// stili del css, racchusi da graffe - servirebbe una regex
			// <body.*?>(.*\/>)
			if (paneText.substring(currentLastIndex, indexLt).contains("{"))
				stop = true;

			if (!stop) {
				String textFound = paneText.substring(currentLastIndex, indexLt);
				if (textFound.trim().length() == 0) {
					continue;
				}

				// System.out.println(textFound);

				Map<Integer, String> fragmentWordPosition = new TreeMap<>();
				fragmentWordPosition = DocumentManipulator.extractWords(textFound, minLength);

				for (Map.Entry<Integer, String> entry : fragmentWordPosition.entrySet())
					wordsAndPosition.put(currentLastIndex + entry.getKey(), entry.getValue());
				currentLastIndex += textFound.length();

			}
			stop = false;
		}
//		wordsAndPosition.forEach((k, v) -> System.out.println(v + " (" + k + ")"));
		return wordsAndPosition;
	}

	public String searchForSimilarities(String targetText, Map<Integer, String> map, Integer searchWindow,
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
		// massimo numero di confronti per Jaro
		int max = (map.size()-currentSearchWindow)*currentSearchWindow + (currentSearchWindow)*(currentSearchWindow-1)/2;
		int count= 0;
		for (int i = 0; i < map.size(); i++) {
			for (int j = i + 1; j <= i+currentSearchWindow && j<map.size(); j++) {
//				if (j >= sortedKeys.size())
//					break;
				count++;
				Double distance = JaroDistance.apply(map.get(sortedKeys.get(i)), map.get(sortedKeys.get(j)));
				if (distance >= threshold) {
//					System.out.println("Distanza di Jaro tra " + Jsoup.parse(map.get(sortedKeys.get(i))).text() + " e "
//							+ Jsoup.parse(map.get(sortedKeys.get(j))).text() + ": " + distance);
					Double oldDistance = indexToReplace.get(sortedKeys.get(i));
					if(oldDistance==null || oldDistance!=null && oldDistance<distance)
						indexToReplace.put(sortedKeys.get(i),distance);
					oldDistance = indexToReplace.get(sortedKeys.get(j));
					if(oldDistance==null || oldDistance!=null && oldDistance<distance)
						indexToReplace.put(sortedKeys.get(j),distance);				}
			}
			UIUtility.progress = (int) (100.0*(count)/max);
//			System.out.println(count+"/"+max+" => "+100.0*(count)/max+"%");
		}
		
		StringBuffer sb = new StringBuffer(targetText);
		for (Entry<Integer,Double> entry : indexToReplace.entrySet()) {
			sb.replace(entry.getKey(), entry.getKey()+ map.get(entry.getKey()).length(),
					"<span style='background-color: "+DocumentManipulator.getColor(entry.getValue(),threshold)+"'>" + map.get(entry.getKey()) + "</span>");
		}
		return sb.toString();
	}

	/***
	 * Search for mispelled words in text and replace them with the correct one. It uses a regex to find words,
	 * but it is worse than its replaceAll counterpart, which performs a lot better.
	 * So use {@link #searchForWrongWordsReplaceAll(String targetText)} method instead.
	 * @param targetText
	 * @return	targetText with mispelled words replaced with red html highlighter
	 */
	public String searchForWrongWords(String targetText){
		String patternToMatch = it.holls.writersutilityM.utils.Utils.itWrongWords.entrySet().stream().map(Entry::getKey).collect(Collectors.joining("|"));
		Pattern pattern = Pattern.compile(patternToMatch);
		Matcher matcher = pattern.matcher(targetText);
		
		Map<Integer, String> wordsAndPosition = new TreeMap<>(Comparator.reverseOrder());
		
		while(matcher.find()){
			wordsAndPosition.put(matcher.start(), matcher.group());
		}
		
		StringBuffer sb = new StringBuffer(targetText);
		for (Entry<Integer, String> entry : wordsAndPosition.entrySet()) {
			sb.replace(entry.getKey(), entry.getKey()+ entry.getValue().length(),
					"<span style='background-color: "+DocumentManipulator.getColor(1.0,1.0)+"'>" + it.holls.writersutilityM.utils.Utils.itWrongWords.get(entry.getValue()) + "</span>");
		}
		return sb.toString();
	}
	
	/***
	 * Search for mispelled words in text and replace them with the correct one. For every word found, it performs a wrap
	 * highlighting word with red color.
	 * @param targetText
	 * @return	targetText with mispelled words replaced with red html highlighter
	 */
	public String searchForWrongWordsReplaceAll(String targetText){
		for(Entry<String, String> entry : it.holls.writersutilityM.utils.Utils.itWrongWords.entrySet()){
			targetText = targetText.replaceAll("\\b"+entry.getKey(), "<span style='background-color: "+DocumentManipulator.getColor(1.0,1.0)+"'>" + entry.getValue() + "</span>");
		}
		
		return targetText;
	}
	
}
