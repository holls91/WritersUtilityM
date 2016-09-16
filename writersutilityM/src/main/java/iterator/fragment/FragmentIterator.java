package iterator.fragment;

import java.util.Iterator;

public abstract class FragmentIterator implements Iterable<Fragment>{

	protected String text;
	
	public FragmentIterator(String text){
		this.text = text;
	}
	
	@Override
	public abstract Iterator<Fragment> iterator();

}
