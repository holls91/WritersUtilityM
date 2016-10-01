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
		
		gui.getEditorPane().setText("");
		
		assertEquals(gui.getEditorPane().getDocument().getLength(), 0);
	}
	
	@Test
	public void getHTMLText(){
		GUI gui = new GUI();
		
		assertEquals(gui.getEditorPane().getDocument().getLength(), 0);
		
		gui.getEditorPane().setContentType("text/html");
		gui.getEditorPane().setText("<html><head></head><body><p>Prova prova</p></body></html>");
		
		assertFalse(gui.getEditorPane().getText().length() == 0);
		assertEquals(gui.getEditorPane().getText().replaceAll("\n", "").replaceAll(">(\\s)*", ">").replaceAll("(\\s)*<", "<"), "<html><head></head><body><p>Prova prova</p></body></html>");
	}
	
	@Test
	public void setAndGetWebviewText(){
		GUI gui = new GUI();
		gui.setFileLoaded(true);
		
		String text = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head></head><body><p>Prova prova</p></body></html>";
		gui.setText(text);
		
		assertEquals(text, gui.getText());
	}
}
