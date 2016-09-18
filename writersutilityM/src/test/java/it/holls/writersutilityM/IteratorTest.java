package it.holls.writersutilityM;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import iterator.HTMLWordIterator;
import iterator.fragment.HTMLFragmentIterator;

public class IteratorTest {

	@Test
	public void fragmentTest() {
		String html = "<html><head></head><body>prova di fragment <b>prova di fragment</b> prova di fragment</body></html>";
		HTMLFragmentIterator fi = new HTMLFragmentIterator(html);
		StringBuilder sb = new StringBuilder();
		while(fi.hasNext()) {
			sb.append(fi.next().getText());
			System.out.println(sb);
		}
		assertEquals("prova di fragment prova di fragment prova di fragment",sb.toString());
	}
	@Test
	public void wordTest() {
		String html = "<html><head></head><body>prova di fragment <b>prova di fragment</b> prova di fragment</body></html>";
		HTMLFragmentIterator fi = new HTMLFragmentIterator(html);
		HTMLWordIterator wi = new HTMLWordIterator(fi);
		StringBuilder sb = new StringBuilder();
		while(wi.hasNext()) {
			sb.append(wi.next().getParola());
			System.out.println(sb+" ");
		}
		assertEquals("prova di fragment prova di fragment prova di fragment",sb.toString());
	}

}
