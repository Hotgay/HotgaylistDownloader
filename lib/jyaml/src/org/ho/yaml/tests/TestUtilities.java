package org.ho.yaml.tests;

import junit.framework.TestCase;
import static org.ho.yaml.Utilities.*;
public class TestUtilities extends TestCase {

	public void testUnscapeShouldUnescapeBackslash(){
		assertEquals("\\t", unescape("\\\\t"));
	}
	
	public void testUnescapeShouldUnescapeTab(){
		assertEquals("\t", unescape("\\t"));
	}
	
	public void testUnescapeDontDoAnything(){
		assertEquals("Herman", unescape("Herman"));
	}
	
	public void testEscapeShouldEscapeCorrectly(){
		assertEquals("\\\\t", escape("\\t"));
	}
	
	public void testEscapeShouldEscapeTab(){
		assertEquals("\\t", escape("\t"));
	}
	
	public void testEscapeDontDoAnything(){
		assertEquals("Herman", unescape("Herman"));
	}
}
