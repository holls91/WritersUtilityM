package iterator;

import java.util.Iterator;

import iterator.fragment.FragmentIterator;

public abstract class WordIterator implements Iterable<Word>, Iterator<Word> {
	
	protected String text = "";
	protected FragmentIterator fragmentIterator;
	
	public WordIterator(FragmentIterator fragmentIterator){
		this.fragmentIterator = fragmentIterator;
	}

	public Iterator<Word> iterator() {
		return this;
	}
}
