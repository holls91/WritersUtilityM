package iterator.fragment;

import java.util.Iterator;

public class HTMLFragmentIterator extends FragmentIterator {

	String bodyRegex = "<body.*?>(.*)</.*?>";

	int currentLastIndex = 0;
	String htmlText = text;
	int indexGt = -1, indexLt = 0;
	// Fare match sull'apertura del tag body e fare .end()
	boolean htmlTag = false;

	public HTMLFragmentIterator(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Iterator<Fragment> iterator() {
		Iterator<Fragment> it = new Iterator<Fragment>() {

			@Override
			public boolean hasNext() {
				indexGt = htmlText.indexOf(">", indexLt);
				if (indexGt == -1) {
					return false;
				}
				try{
				if (text.substring(indexLt, indexGt + 1).equals("<style>"))
					htmlTag = true;
				} catch (IndexOutOfBoundsException e){
					return false;
				}

				indexLt = htmlText.indexOf("<", indexGt);
				if (indexLt == -1) {
					return false;
				}
				return true;
			}

			@Override
			public Fragment next() {

				currentLastIndex = indexGt + 1;

				// FIX-ME: soluzione provvisoria per escludere la ricerca dentro
				// gli
				// stili del css, racchusi da graffe - servirebbe una regex
				// <body.*?>(.*\/>)
				if (text.substring(currentLastIndex, indexLt).contains("{"))
					htmlTag = true;

				if (!htmlTag) {
					String textFound = text.substring(currentLastIndex, indexLt);
					if (textFound.trim().length() != 0) {
						Fragment fragment = new Fragment(textFound, currentLastIndex);
						currentLastIndex += textFound.length(); // ha senso?
						return fragment;
					}

				}
				htmlTag = false;
				return new Fragment("", currentLastIndex);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		return it;
	}

}
