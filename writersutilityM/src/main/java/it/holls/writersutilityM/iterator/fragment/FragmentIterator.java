package it.holls.writersutilityM.iterator.fragment;

import java.util.Iterator;

public abstract class FragmentIterator implements Iterable<Fragment>, Iterator<Fragment> {

	protected String text;
	
	public FragmentIterator(String text){
		this.text = text;
	}
	
	@Override
	public Iterator<Fragment> iterator() {
		return this;
	}

}
