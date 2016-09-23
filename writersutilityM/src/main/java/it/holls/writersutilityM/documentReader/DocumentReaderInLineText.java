package it.holls.writersutilityM.documentReader;

public class DocumentReaderInLineText extends DocumentReader {

	@Override
	public String loadAndConvertToHTML(String inputText) {
		// TODO Auto-generated method stub
		return inputText;
	}

//	@Override
//	public Map<Integer, String> extractWordsFromHTML(String paneText, Integer minLength) {
////		int currentLastIndex = 0, indexLt = 0;
//
//		BreakIterator wordIterator = BreakIterator.getWordInstance();
//		
//		wordIterator.setText(paneText);
//	    int start = wordIterator.first();
//	    int end = wordIterator.next();
//
//	    Map<Integer, String> wordsAndPosition = new TreeMap<>();
//
//	    while (end != BreakIterator.DONE) {
//	        String word = paneText.substring(start,end);
//	        if(word.length()>=minLength)					//Se non è impostata una lunghezza minima, mettere 0
//	        	wordsAndPosition.put(start, word);
//	        start = end;
//	        end = wordIterator.next();
//	    }
//		return wordsAndPosition;
//
//	}
}
