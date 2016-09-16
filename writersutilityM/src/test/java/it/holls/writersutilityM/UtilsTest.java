package it.holls.writersutilityM;

import static org.junit.Assert.assertEquals;

import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testWrongWordsJoinedKeys() {
		String patternToMatch = utils.Utils.itWrongWords.entrySet().stream().map(Entry::getKey).collect(Collectors.joining("|"));
		assertEquals("E'|qual'è|pultroppo|poich&#232;|finch&#232;|poiche'|perche'|p&#242;|s&#242;|qu&#224;|perch&#232;|sé stesso|entusiasto|qu&#236;|s&#224;|st&#242;", patternToMatch);
	}
	
	@Test
	public void testWrongWordsJoinedValues() {
		String patternToMatch = utils.Utils.itWrongWords.entrySet().stream().map(Entry::getValue).collect(Collectors.joining("|"));
		assertEquals("&#200;|qual è|purtroppo|poich&#233;|finch&#233;|poich&#233;|perch&#233;|po'|so|qua|perch&#233;|se stesso|entusiasta|qui|sa|sto", patternToMatch);
	}

}
