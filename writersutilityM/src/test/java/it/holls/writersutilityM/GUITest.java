package it.holls.writersutilityM;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import it.holls.writersutilityM.ui.GUI;


public class GUITest {

	@Test
	public void getRightText(){
		GUI gui = new GUI();
		
		assertEquals(gui.getEditorPane().getDocument().getLength(), 0);
		
		gui.getEditorPane().setText("Ciao");
		
		assertFalse(gui.getEditorPane().getDocument().getLength() == 0);
	}
}
