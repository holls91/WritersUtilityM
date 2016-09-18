package it.holls.writersutilityM;

import static org.junit.Assert.assertEquals;

import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testWrongWordsJoinedKeys() {
		String patternToMatch = utils.Utils.itWrongWords.entrySet().stream().map(Entry::getKey).collect(Collectors.joining("|"));
		assertEquals("E&#8217;|entusiasto|finch&#232;|p&#242;|perch&#232;|perche&#8217;|poich&#232;|poiche&#8217;|pultroppo|qu&#224;|qu&#236;|qual&#8217;&#232;|s&#224;|s&#242;|st&#242;|sé stesso", patternToMatch);
	}
	
	@Test
	public void testWrongWordsJoinedValues() {
		String patternToMatch = utils.Utils.itWrongWords.entrySet().stream().map(Entry::getValue).collect(Collectors.joining("|"));
		assertEquals("&#200;|entusiasta|finch&#233;|po&#8217;|perch&#233;|perch&#233;|poich&#233;|poich&#233;|purtroppo|qua|qui|qual &#232;|sa|so|sto|se stesso", patternToMatch);
	}

}
