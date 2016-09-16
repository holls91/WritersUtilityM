package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.html.HTMLDocument;

import org.apache.poi.xwpf.converter.core.XWPFConverterException;

import it.holls.writersutilityM.DocumentManipulator;

public class UI {

	private JFrame frame;
	private JTextField textField;
	private String text;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Stream.of(UIManager.getInstalledLookAndFeels())
		  .filter(look->"Nimbus".equals(look.getName()))
		  .findFirst()
		  .ifPresent(UI::setLookAndFeel);

		EventQueue.invokeLater(() -> new UI().frame.setVisible(true));
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Writer's Utility");
		frame.setBounds(100, 100, 781, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmApri = new JMenuItem("Apri...");

		mnFile.add(mntmApri);

		JMenuItem mntmEsci = new JMenuItem("Esci");
		mnFile.add(mntmEsci);

		JMenu mnAzioni = new JMenu("Azioni");
		menuBar.add(mnAzioni);

		JMenuItem mntmCercaRipetizioni = new JMenuItem("Cerca ripetizioni");

		mnAzioni.add(mntmCercaRipetizioni);
		
		this.textField = new JTextField();
		this.textField.setText("15");
		menuBar.add(this.textField);
		this.textField.setColumns(5);
		
		JSlider slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setValue(80);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(70);
		menuBar.add(slider);

		final JEditorPane editorPane = new JEditorPane();
		
		frame.getContentPane().add(editorPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(editorPane);
		frame.getContentPane().add(scrollPane);
		
		// *********** Action Listeners ************//
		mntmApri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				File file = new File("C:\\Users\\Gattino\\Documents\\SampleDocx.docx");
				
//				JFileChooser fileChooser = new JFileChooser();
//				int returnVal = fileChooser.showOpenDialog(frame);

				try {
//					File file = null;
//					if (returnVal == JFileChooser.APPROVE_OPTION) {
//						file = fileChooser.getSelectedFile();
//					}

					text = DocumentManipulator.convertToHTML(new FileInputStream(file));
					editorPane.setText(text);
					editorPane.setContentType("text/html");
					editorPane.setEditable(false);
					text = editorPane.getText();
//					editorPane.setText(DocumentManipulator.getParagraphText(DocumentManipulator.openFile(file)));
				} catch (XWPFConverterException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		mntmCercaRipetizioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HTMLDocument document = (HTMLDocument) editorPane.getDocument();
				Map<Integer, String> words = DocumentManipulator.extractWordsFromHTML(text, 4);
//				Map<Integer, String> words = DocumentManipulator.extractWords(editorPane.getText(), 3);
				String newText = DocumentManipulator.searchForSimilarities(text, words, Integer.valueOf(textField.getText()), Double.valueOf(slider.getValue())/100);
				editorPane.setText(newText);
			}
		});
	}

	private static void setLookAndFeel(LookAndFeelInfo look) {
		try {
			UIManager.setLookAndFeel(look.getClassName());
			UIDefaults defaults = UIManager.getLookAndFeelDefaults();
			defaults.put("nimbusOrange",defaults.get("nimbusBase"));
		}
		catch(ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
}
