package iterator;

import java.util.Iterator;

import iterator.fragment.FragmentIterator;

public abstract class WordIterator implements Iterable<Word> {
	
	protected String text = "";
	protected FragmentIterator fragmentIterator;
	
	public WordIterator(FragmentIterator fragmentIterator){
		this.fragmentIterator = fragmentIterator;
	}

	public abstract Iterator<Word> iterator();
}
