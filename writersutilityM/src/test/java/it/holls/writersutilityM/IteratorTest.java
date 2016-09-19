package it.holls.writersutilityM;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import iterator.HTMLWordIterator2;
import iterator.fragment.HTMLFragmentIterator;

public class IteratorTest {

	@Test
	public void fragmentTest() {
		String html = "<html><head></head><body>prova di fragment <b>seconda prova di fragment</b> terza prova di fragment</body></html>";
		HTMLFragmentIterator fi = new HTMLFragmentIterator(html);
		StringBuilder sb = new StringBuilder();
		while(fi.hasNext()) {
			sb.append(fi.next().getText());
			System.out.println(sb);
		}
		assertEquals("prova di fragment seconda prova di fragment terza prova di fragment",sb.toString());
	}
	
	@Test
	public void wordTest() {
		String html = "<html><head></head><body>prova di fragment 1!!! &#;<b>prova di fragment 2 ,?543 </b> prova di fragment 3</body></html>";
		HTMLFragmentIterator fi = new HTMLFragmentIterator(html);
		HTMLWordIterator2 wi = new HTMLWordIterator2(fi, 1);
		StringBuilder sb = new StringBuilder();
		while(wi.hasNext()) {
			sb.append(wi.next().getParola()+" ");
			System.out.println(sb+" ");
		}
		assertEquals("prova di fragment prova di fragment prova di fragment ",sb.toString());
	}
	
	@Test
	public void accentedWordTest() {
		String html = "<html><head></head><body>FILONE FANTASY:<b>syria &#232; un cavaliere della luce di categoria bianca, nonch&#233;</b></body></html>";
		HTMLFragmentIterator fi = new HTMLFragmentIterator(html);
		HTMLWordIterator2 wi = new HTMLWordIterator2(fi, 0);
		StringBuilder sb = new StringBuilder();
		while(wi.hasNext()) {
			sb.append(wi.next().getParola()+" ");
			System.out.println(sb+" ");
		}
		assertEquals("FILONE FANTASY syria &#232; un cavaliere della luce di categoria bianca nonch&#233; ",sb.toString());
	}
	
	@Test
	public void accentedWordTestMinLength() {
		String html = "<html><head></head><body>FILONE FANTASY:<b>syria &#232; un cavaliere della luce di categoria bianca, nonch&#233;</b></body></html>";
		HTMLFragmentIterator fi = new HTMLFragmentIterator(html);
		HTMLWordIterator2 wi = new HTMLWordIterator2(fi, 5);
		StringBuilder sb = new StringBuilder();
		while(wi.hasNext()) {
			sb.append(wi.next().getParola()+" ");
			System.out.println(sb+" ");
		}
		assertEquals("FILONE FANTASY cavaliere categoria bianca nonch&#233; ",sb.toString());
	}
	
	@Test
	public void accentedWordTestStyled() {
		String html = "<html><head><style>p{margin-top:0pt;margin-bottom:1pt;}span.Collegamentoipertestuale{color:#0000ff;text-decoration:underline;}p.NormaleWeb{text-align:left;margin-top:5.0pt;margin-bottom:5.0pt;}span.NormaleWeb{font-family:'Times New Roman';font-size:12.0pt;}</style></head><body>FILONE FANTASY:<b>syria &#232; un cavaliere della luce di categoria bianca, nonch&#233;</b></body></html>";
		HTMLFragmentIterator fi = new HTMLFragmentIterator(html);
		HTMLWordIterator2 wi = new HTMLWordIterator2(fi, 5);
		StringBuilder sb = new StringBuilder();
		while(wi.hasNext()) {
			sb.append(wi.next().getParola()+" ");
			System.out.println(sb+" ");
		}
		assertEquals("FILONE FANTASY cavaliere categoria bianca nonch&#233; ",sb.toString());
	}

}
