package it.holls.writersutilityM.iterator.fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLFragmentIterator extends FragmentIterator {

	String bodyRegex = "<body.*?>([\\s\\S]*)</.*?>";

	int currentLastIndex = 0;
	String htmlText = text;
	int indexGt = -1, indexLt = 0;
	boolean htmlTag = false;
	Fragment fragment;
	Pattern pattern;
	Matcher matcher;

	public HTMLFragmentIterator(String text) {
		super(text);
		pattern = Pattern.compile(bodyRegex);
		matcher = pattern.matcher(text);
		if(matcher.find()){
			indexLt = matcher.start();
		}
	}

	@Override
	public boolean hasNext() {
		indexGt = htmlText.indexOf(">", indexLt);
		if (indexGt == -1) {
			return false;
		}
        
		indexLt = htmlText.indexOf("<", indexGt);
		if (indexLt == -1) {
			return false;
		}

		currentLastIndex = indexGt + 1;

		if (!htmlTag) {
			String textFound = text.substring(currentLastIndex, indexLt);
			if (textFound.trim().length() != 0) {
				fragment = new Fragment(textFound, currentLastIndex);
			} else
				return hasNext();

		}
		htmlTag = false;

		return true;
	}

	@Override
	public Fragment next() {
		return fragment;

	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
