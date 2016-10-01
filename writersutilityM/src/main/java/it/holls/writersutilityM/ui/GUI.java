package it.holls.writersutilityM.ui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.apache.poi.xwpf.converter.core.XWPFConverterException;

import it.holls.writersutilityM.documentProcessor.DocumentProcessor;
import it.holls.writersutilityM.documentReader.DocumentReader;
import it.holls.writersutilityM.documentReader.DocumentReaderInLineText;
import it.holls.writersutilityM.documentReader.FactoryDocumentReader;
import it.holls.writersutilityM.iterator.HTMLWordIterator2;
import it.holls.writersutilityM.iterator.Word;
import it.holls.writersutilityM.iterator.WordIterator;
import it.holls.writersutilityM.iterator.fragment.FragmentIterator;
import it.holls.writersutilityM.iterator.fragment.HTMLFragmentIterator;
import it.holls.writersutilityM.utils.Utils;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class GUI {

	private JFrame frmWritersUtility;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmApri;
	private JMenuItem mntmEsci;

	private String text = "", newText = "";

	JFXPanel jfxPanel = new JFXPanel();
	WebView webView;
	private String extension = "";
	private boolean fileLoaded = false;

	private DocumentReader documentReader = new DocumentReaderInLineText();
	private DocumentProcessor documentProcessor = new DocumentProcessor();
	private FragmentIterator fi;
	private WordIterator wi;
	
//	private Task task;
	private JTabbedPane tabbedPane;
	private JScrollPane scrollPane;
	private JEditorPane editorPane;
	
	private JPanel panelRipetizioni;
	private JSpinner spinnerFinestra;
	private JLabel lblFinestraDiRicerca;
	private JSlider sliderAccuratezza;
	private JLabel lblNewLabel;
	private JLabel lblLunghezzaMinimaDelle;
	private JSpinner spinnerLunghezza;
	private JButton btnAnalizza;
	private GroupLayout groupLayout;
	private JPanel panelErrori;
	private JButton button;
//	private JMenu mnOpzioni;
//	private JMenu mnDocx;
//	private JCheckBoxMenuItem mntmPoi;
//	private JCheckBoxMenuItem mntmDocxj;
	
	public static String menuSelection = "POI";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Stream.of(UIManager.getInstalledLookAndFeels()).filter(look -> "Nimbus".equals(look.getName())).findFirst()
				.ifPresent(GUI::setLookAndFeel);

		EventQueue.invokeLater(() -> new GUI().frmWritersUtility.setVisible(true));
	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frmWritersUtility = new JFrame();
		this.frmWritersUtility.setTitle("Writer's Utility");
		this.frmWritersUtility.setBounds(100, 100, 800, 500);
		this.frmWritersUtility.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frmWritersUtility.setJMenuBar(getMenuBar());
		groupLayout = new GroupLayout(frmWritersUtility.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(getScrollPane(), GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
				.addComponent(getTabbedPane_1(), Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 784, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(getTabbedPane_1(), GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(getScrollPane(), GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
		);
		frmWritersUtility.getContentPane().setLayout(groupLayout);
	}

	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnFile());
//			menuBar.add(getMnOpzioni());
		}
		return menuBar;
	}

	private JFXPanel getJFXPanel() {
		if (jfxPanel == null) {
			jfxPanel = new JFXPanel();
		}
		return jfxPanel;
	}

	private JMenu getMnFile() {
		if (mnFile == null) {
			mnFile = new JMenu("File");
			mnFile.add(getMntmApri());
			mnFile.add(getMntmEsci());
		}
		return mnFile;
	}

	private JMenuItem getMntmApri() {
		if (mntmApri == null) {
			mntmApri = new JMenuItem("Apri");
			mntmApri.addActionListener(e -> {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(
						new FileNameExtensionFilter("File di testo", "docx", "doc", "odt", "rtf", "txt"));
				int returnVal = fileChooser.showOpenDialog(frmWritersUtility);

				try {
					File file = null;
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
						fileLoaded = true;
						try{
							groupLayout.replace(getScrollPane(), getJFXPanel());
						}
						catch(IllegalArgumentException iae){
							//Eccezione catturata nel caso in cui sia già stato effettuato il passaggio
							//da in-line a file caricato
						}

						frmWritersUtility.setTitle("Writer's Utility" + " - " + file.getName());

						extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
						
						documentReader = FactoryDocumentReader.getDocumentReader(extension);
						text = Utils.fixHtml(documentReader.loadAndConvertToHTML(file.getPath()));
						text = text.replaceAll("width:\\d{1,}(?:[,\\.]\\d{1,})?pt", "width:90%");
//						text = text.replaceAll("margin-(?:\\w)+:\\d{1,}(?:[,\\.]\\d{1,})?pt", "margin: 2em");
						text = text.replaceAll("margin-left:\\d{1,}(?:[,\\.]\\d{1,})?pt", "margin-left: 5%");
						System.out.println(text);

						editorPane.setText("");
						// Creation of scene and future interactions with
						// JFXPanel
						// should take place on the JavaFX Application Thread
						Platform.runLater(() -> {
							webView = new WebView();
							jfxPanel.setScene(new Scene(webView));
							webView.getEngine().loadContent(text);
						});
						this.newText = text;
					}

					// editorPane.setText(DocumentManipulator.getParagraphText(DocumentManipulator.openFile(file)));
				} catch (XWPFConverterException e1) {
					e1.printStackTrace();
				}
			});

		}
		return mntmApri;
	}

	private JMenuItem getMntmEsci() {
		if (mntmEsci == null) {
			mntmEsci = new JMenuItem("Esci");
		}
		return mntmEsci;
	}

	private static void setLookAndFeel(LookAndFeelInfo look) {
		try {
			UIManager.setLookAndFeel(look.getClassName());
			UIDefaults defaults = UIManager.getLookAndFeelDefaults();
			defaults.put("nimbusOrange", defaults.get("nimbusBase"));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	private JTabbedPane getTabbedPane_1() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			JPanel pippo = getPanelRipetizioni();
			pippo.setBounds(0, 0, 110, 110);
			tabbedPane.addTab("Ripetizioni", null, getPanelRipetizioni(), null);
			tabbedPane.addTab("Errori", null, getPanelErrori(), null);
		}
		return tabbedPane;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getEditorPane());
		}
		return scrollPane;
	}
	public JEditorPane getEditorPane() {
		if (editorPane == null) {
			editorPane = new JEditorPane();
		}
		return editorPane;
	}
	

	// PANEL RIPRESI
	private JPanel getPanelRipetizioni() {
		if (panelRipetizioni == null) {
			panelRipetizioni = new JPanel();
			GridBagLayout gbl_panelRipetizioni = new GridBagLayout();
			gbl_panelRipetizioni.columnWidths = new int[] { 41, 136, 57, 24, 109, 200, 27, 167, 0 };
			gbl_panelRipetizioni.rowHeights = new int[] { 45, 33, 336, 0 };
			gbl_panelRipetizioni.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
					Double.MIN_VALUE };
			gbl_panelRipetizioni.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
			panelRipetizioni.setLayout(gbl_panelRipetizioni);
			GridBagConstraints gbc_lblFinestraDiRicerca = new GridBagConstraints();
			gbc_lblFinestraDiRicerca.anchor = GridBagConstraints.WEST;
			gbc_lblFinestraDiRicerca.insets = new Insets(0, 0, 5, 5);
			gbc_lblFinestraDiRicerca.gridx = 1;
			gbc_lblFinestraDiRicerca.gridy = 0;
			panelRipetizioni.add(getLblFinestraDiRicerca(), gbc_lblFinestraDiRicerca);
			GridBagConstraints gbc_spinnerFinestra = new GridBagConstraints();
			gbc_spinnerFinestra.anchor = GridBagConstraints.SOUTHWEST;
			gbc_spinnerFinestra.insets = new Insets(0, 0, 5, 5);
			gbc_spinnerFinestra.gridx = 2;
			gbc_spinnerFinestra.gridy = 0;
			panelRipetizioni.add(getSpinnerFinestra(), gbc_spinnerFinestra);
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 4;
			gbc_lblNewLabel.gridy = 0;
			panelRipetizioni.add(getLblNewLabel(), gbc_lblNewLabel);
			GridBagConstraints gbc_sliderAccuratezza = new GridBagConstraints();
			gbc_sliderAccuratezza.fill = GridBagConstraints.HORIZONTAL;
			gbc_sliderAccuratezza.anchor = GridBagConstraints.NORTH;
			gbc_sliderAccuratezza.insets = new Insets(0, 0, 5, 5);
			gbc_sliderAccuratezza.gridheight = 2;
			gbc_sliderAccuratezza.gridx = 5;
			gbc_sliderAccuratezza.gridy = 0;
			panelRipetizioni.add(getSliderAccuratezza(), gbc_sliderAccuratezza);
			GridBagConstraints gbc_btnAnalizza = new GridBagConstraints();
			gbc_btnAnalizza.fill = GridBagConstraints.BOTH;
			gbc_btnAnalizza.insets = new Insets(0, 0, 5, 0);
			gbc_btnAnalizza.gridheight = 2;
			gbc_btnAnalizza.gridx = 7;
			gbc_btnAnalizza.gridy = 0;
			panelRipetizioni.add(getBtnAnalizza(), gbc_btnAnalizza);
			GridBagConstraints gbc_lblLunghezzaMinimaDelle = new GridBagConstraints();
			gbc_lblLunghezzaMinimaDelle.anchor = GridBagConstraints.WEST;
			gbc_lblLunghezzaMinimaDelle.insets = new Insets(0, 0, 5, 5);
			gbc_lblLunghezzaMinimaDelle.gridx = 1;
			gbc_lblLunghezzaMinimaDelle.gridy = 1;
			panelRipetizioni.add(getLblLunghezzaMinimaDelle(), gbc_lblLunghezzaMinimaDelle);
			GridBagConstraints gbc_spinnerLunghezza = new GridBagConstraints();
			gbc_spinnerLunghezza.anchor = GridBagConstraints.NORTHWEST;
			gbc_spinnerLunghezza.insets = new Insets(0, 0, 5, 5);
			gbc_spinnerLunghezza.gridx = 2;
			gbc_spinnerLunghezza.gridy = 1;
			panelRipetizioni.add(getSpinnerLunghezza(), gbc_spinnerLunghezza);
		}
		return panelRipetizioni;
	}

	private JLabel getLblFinestraDiRicerca() {
		if (lblFinestraDiRicerca == null) {
			lblFinestraDiRicerca = new JLabel("Finestra di ricerca:");
		}
		return lblFinestraDiRicerca;
	}

	private JSpinner getSpinnerFinestra() {
		if (spinnerFinestra == null) {
			spinnerFinestra = new JSpinner();
			spinnerFinestra.setModel(new SpinnerNumberModel(15, 1, 100, 1));
		}
		return spinnerFinestra;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Accuratezza ricerca:");
		}
		return lblNewLabel;
	}

	private JSlider getSliderAccuratezza() {
		if (sliderAccuratezza == null) {
			sliderAccuratezza = new JSlider();
			sliderAccuratezza.setValue(85);
			sliderAccuratezza.setSnapToTicks(true);
			sliderAccuratezza.setPaintLabels(true);
			sliderAccuratezza.setPaintTicks(true);
			sliderAccuratezza.setMinorTickSpacing(1);
			sliderAccuratezza.setMajorTickSpacing(5);
			sliderAccuratezza.setMinimum(70);
		}
		return sliderAccuratezza;
	}

	private JLabel getLblLunghezzaMinimaDelle() {
		if (lblLunghezzaMinimaDelle == null) {
			lblLunghezzaMinimaDelle = new JLabel("Lunghezza minima delle parole:");
		}
		return lblLunghezzaMinimaDelle;
	}

	private JSpinner getSpinnerLunghezza() {
		if (spinnerLunghezza == null) {
			spinnerLunghezza = new JSpinner();
			spinnerLunghezza.setModel(new SpinnerNumberModel(new Integer(4), new Integer(2), null, new Integer(1)));
		}
		return spinnerLunghezza;
	}

	public JButton getBtnAnalizza() {
		if (btnAnalizza == null) {
			btnAnalizza = new JButton("Analizza");
			UIUtility.analyzeButton = btnAnalizza;
			btnAnalizza.addActionListener(e -> {
				// Instances of javax.swing.SwingWorker are not reusuable, so
				// we create new instances as needed.
			});
			btnAnalizza.addActionListener(e -> {

				//Operazioni necessarie se si scrive direttamente nel pane
				if (!fileLoaded) {
					try {
						text = Utils.createHtmlFromText(
								editorPane.getDocument().getText(0, editorPane.getDocument().getLength()));
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					editorPane.setContentType("text/html");
				}
				// HTMLDocument document = (HTMLDocument)
				// editorPane.getDocument();
//				Map<Integer, String> words = documentReader.extractWordsFromHTML(text,
//						(int) spinnerLunghezza.getValue()-1);
				Map<Integer, String> words = new TreeMap<>();
				fi = new HTMLFragmentIterator(text);
				wi = new HTMLWordIterator2(fi, (int) spinnerLunghezza.getValue()-1);
				while(wi.hasNext()) {
					Word word = wi.next();
					words.put(word.getPosition(), word.getParola());
				}
				newText = documentProcessor.searchForSimilarities(text, words,
						(int) spinnerFinestra.getValue(), Double.valueOf(sliderAccuratezza.getValue()) / 100);
				Platform.runLater(() -> {
					webView = new WebView();
					jfxPanel.setScene(new Scene(webView));
					webView.getEngine().loadContent(newText);
				});

				System.out.println(newText);
				editorPane.setText(newText);
			});
		}
		return btnAnalizza;
	}	
	private JPanel getPanelErrori() {
		if (panelErrori == null) {
			panelErrori = new JPanel();
			panelErrori.add(getButton());
		}
		return panelErrori;
	}
	private JButton getButton() {
		if (button == null) {
			button = new JButton("Correggi errori");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Operazioni necessarie se si scrive direttamente nel pane
					if (!fileLoaded) {
						try {
							text = Utils.createHtmlFromText(
									editorPane.getDocument().getText(0, editorPane.getDocument().getLength()));
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
						editorPane.setContentType("text/html");
					}
					newText = documentProcessor.searchForWrongWordsReplaceAll(text);
					Platform.runLater(() -> {
						webView = new WebView();
						jfxPanel.setScene(new Scene(webView));
						webView.getEngine().loadContent(newText);
					});

					System.out.println(newText);
					editorPane.setText(newText);
				}
			});
		}
		return button;
	}

	public String getOriginalText(){
		return text;
	}
	
	public void setOriginalText(String text){
		this.text = text;
		this.setText(text);
	}
	
	public String getText() {
		return fileLoaded ? newText : getEditorPane().getText();
	}
	
	public void setText(String text){
		if(fileLoaded){
			Platform.runLater(() -> {
				webView = new WebView();
				jfxPanel.setScene(new Scene(webView));
				webView.getEngine().loadContent(text);
			});
		}
		else {
			if(!getEditorPane().getContentType().equals("text/html"))
				getEditorPane().setContentType("text/html");
			getEditorPane().setText(text);
		}
		this.newText = text;
	}

	public FragmentIterator getFragmentIterator(){
		return fi;
	}
	
	public WordIterator getWordIterator() {
		return wi;
	}

	public DocumentProcessor getDocumentProcessor() {
		return documentProcessor;
	}

	public void setFileLoaded(boolean loaded){
		this.fileLoaded = loaded;
	}
	
}
