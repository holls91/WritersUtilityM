package it.holls.writersutilityM.ui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.apache.poi.xwpf.converter.core.XWPFConverterException;

import it.holls.writersutilityM.documentProcessor.DocumentProcessor;
import it.holls.writersutilityM.documentProcessor.DocumentProcessorInLineText;
import it.holls.writersutilityM.documentProcessor.FactoryDocumentReader;
import it.holls.writersutilityM.utils.Utils;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class GUI {

	private JFrame frmWritersUtility;
	private JMenuBar menuBar;
	private JTabbedPane tabbedPane;
	private JPanel panelRipetizioni;
	private JScrollPane scrollPane;
	private JEditorPane editorPane;
	private JLabel lblFinestraDiRicerca;
	private JSpinner spinnerFinestra;
	private JLabel lblNewLabel;
	private JSlider sliderAccuratezza;
	private JLabel lblLunghezzaMinimaDelle;
	private JSpinner spinnerLunghezza;
	private JButton btnAnalizza;
	private JMenu mnFile;
	private JMenuItem mntmApri;
	private JMenuItem mntmEsci;

	private String text = "";
	private JPanel panelAltro;

	JFXPanel jfxPanel = new JFXPanel();
	WebView webView;
	private String extension = "";
	private boolean fileLoaded = false;

	private DocumentProcessor documentReader = new DocumentProcessorInLineText();
	private JProgressBar progressBar;
	
	private UIUtility uiUtility;

	private Task task;
	private JPanel panel;
	private JLabel label;
	private JSpinner spinner;
	private JLabel label_1;
	private JSlider slider;
	private JButton button;
	private JLabel label_2;
	private JSpinner spinner_1;
	private JScrollPane scrollPane_1;
	private JProgressBar progressBar_1;
	
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
		this.uiUtility = new UIUtility();
		this.frmWritersUtility = new JFrame();
		this.frmWritersUtility.setTitle("Writer's Utility");
		this.frmWritersUtility.setBounds(100, 100, 800, 500);
		this.frmWritersUtility.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frmWritersUtility.setJMenuBar(getMenuBar());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{784, 0};
		gridBagLayout.rowHeights = new int[]{110, 175, 209, 17, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmWritersUtility.getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.anchor = GridBagConstraints.NORTH;
		gbc_tabbedPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		this.frmWritersUtility.getContentPane().add(getTabbedPane(), gbc_tabbedPane);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.anchor = GridBagConstraints.WEST;
		gbc_scrollPane.fill = GridBagConstraints.VERTICAL;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		frmWritersUtility.getContentPane().add(getScrollPane(), gbc_scrollPane);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.anchor = GridBagConstraints.NORTH;
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 3;
		frmWritersUtility.getContentPane().add(getProgressBar(), gbc_progressBar);
	}

	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnFile());
		}
		return menuBar;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Ripetizioni", null, getPanelRipetizioni(), null);
			tabbedPane.addTab("Errori", null, getPanelAltro(), null);
		}
		return tabbedPane;
	}

	private JPanel getPanelRipetizioni() {
		if (panelRipetizioni == null) {
			panelRipetizioni = new JPanel();
			GridBagLayout gbl_panelRipetizioni = new GridBagLayout();
			gbl_panelRipetizioni.columnWidths = new int[] { 23, 136, 57, 24, 109, 200, 40, 167, 0 };
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

	private JPanel getPanelAltro() {
		if (panelAltro == null) {
			panelAltro = new JPanel();
			GridBagLayout gbl_panelAltro = new GridBagLayout();
			gbl_panelAltro.columnWidths = new int[] { 784, 0 };
			gbl_panelAltro.rowHeights = new int[] { 408, 0 };
			gbl_panelAltro.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_panelAltro.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
			panelAltro.setLayout(gbl_panelAltro);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			panelAltro.add(getPanel(), gbc_panel);
		}
		return panelAltro;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getEditorPane());
			// scrollPane.setViewportView(getJFXPanel());
		}
		return scrollPane;
	}

	private JEditorPane getEditorPane() {
		if (editorPane == null) {
			editorPane = new JEditorPane();
		}
		return editorPane;
	}

	private JFXPanel getJFXPanel() {
		if (jfxPanel == null) {
			jfxPanel = new JFXPanel();
		}
		return jfxPanel;
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
				btnAnalizza.setEnabled(false);
				// Instances of javax.swing.SwingWorker are not reusuable, so
				// we create new instances as needed.
				task = new Task(this,evt -> {
					if("progress"==evt.getPropertyName()) {
						int progress = (Integer)evt.getNewValue();
						progressBar.setValue(progress);
					}
				});
				task.execute();
			});
			btnAnalizza.addActionListener(e -> {

				//Operazioni necessarie se si scrive direttamente nel pane
				if (!fileLoaded) {
					try {
						text = Utils.createHtmlFromText(
								editorPane.getDocument().getText(0, editorPane.getDocument().getLength()));
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					editorPane.setContentType("text/html");
				}
				// HTMLDocument document = (HTMLDocument)
				// editorPane.getDocument();
				Map<Integer, String> words = documentReader.extractWordsFromHTML(text,
						(int) spinnerLunghezza.getValue());
				String newText = documentReader.searchForSimilarities(text, words,
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

				// String path = "resources/Qualche settimana dopo.doc";
				// File file = new File(path);

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(
						new FileNameExtensionFilter("File di testo", "docx", "doc", "odt", "rtf", "txt"));
				int returnVal = fileChooser.showOpenDialog(frmWritersUtility);

				try {
					File file = null;
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
						fileLoaded = true;
						panelRipetizioni.remove(getScrollPane());
						panelRipetizioni.add(getJFXPanel(), getScrollPane());
						// You should execute this part on the Event Dispatch
						// Thread
						// because it modifies a Swing component
						// JFXPanel jfxPanel = new JFXPanel();
						// frmWritersUtility.add(jfxPanel);

						frmWritersUtility.setTitle("Writer's Utility" + " - " + file.getName());

						extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
						documentReader = FactoryDocumentReader.getDocumentReader(extension);
						text = Utils.fixHtml(documentReader.loadAndConvertToHTML(file.getPath()));
						text = text.replaceAll("width:\\d{1,}(?:[,\\.]\\d{1,})?pt", "width:90%");
						System.out.println(text);
						// editorPane.setContentType("text/html");
						// editorPane.setText(text);
						// editorPane.setEditable(false);
						// text = editorPane.getText();

						// Creation of scene and future interactions with
						// JFXPanel
						// should take place on the JavaFX Application Thread
						Platform.runLater(() -> {
							webView = new WebView();
							jfxPanel.setScene(new Scene(webView));
							webView.getEngine().loadContent(text);
						});
					}

					// editorPane.setText(DocumentManipulator.getParagraphText(DocumentManipulator.openFile(file)));
				} catch (XWPFConverterException e1) {
					// TODO Auto-generated catch block
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
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar(0, 100);
	        progressBar.setValue(0);
	        progressBar.setStringPainted(true);
			UIUtility.progressBar = progressBar;
		}
		return progressBar;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{23, 136, 57, 24, 109, 200, 40, 167, 0};
			gbl_panel.rowHeights = new int[]{45, 33, 336, 0, 0};
			gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.WEST;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 1;
			gbc_label.gridy = 0;
			panel.add(getLabel(), gbc_label);
			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.anchor = GridBagConstraints.SOUTHWEST;
			gbc_spinner.insets = new Insets(0, 0, 5, 5);
			gbc_spinner.gridx = 2;
			gbc_spinner.gridy = 0;
			panel.add(getSpinner(), gbc_spinner);
			GridBagConstraints gbc_label_1 = new GridBagConstraints();
			gbc_label_1.anchor = GridBagConstraints.WEST;
			gbc_label_1.insets = new Insets(0, 0, 5, 5);
			gbc_label_1.gridx = 4;
			gbc_label_1.gridy = 0;
			panel.add(getLabel_1(), gbc_label_1);
			GridBagConstraints gbc_slider = new GridBagConstraints();
			gbc_slider.fill = GridBagConstraints.HORIZONTAL;
			gbc_slider.anchor = GridBagConstraints.NORTH;
			gbc_slider.gridheight = 2;
			gbc_slider.insets = new Insets(0, 0, 5, 5);
			gbc_slider.gridx = 5;
			gbc_slider.gridy = 0;
			panel.add(getSlider(), gbc_slider);
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.fill = GridBagConstraints.BOTH;
			gbc_button.gridheight = 2;
			gbc_button.insets = new Insets(0, 0, 5, 0);
			gbc_button.gridx = 7;
			gbc_button.gridy = 0;
			panel.add(getButton(), gbc_button);
			GridBagConstraints gbc_label_2 = new GridBagConstraints();
			gbc_label_2.anchor = GridBagConstraints.WEST;
			gbc_label_2.insets = new Insets(0, 0, 5, 5);
			gbc_label_2.gridx = 1;
			gbc_label_2.gridy = 1;
			panel.add(getLabel_2(), gbc_label_2);
			GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
			gbc_spinner_1.anchor = GridBagConstraints.NORTHWEST;
			gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
			gbc_spinner_1.gridx = 2;
			gbc_spinner_1.gridy = 1;
			panel.add(getSpinner_1(), gbc_spinner_1);
			GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
			gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
			gbc_scrollPane_1.gridwidth = 8;
			gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane_1.gridx = 0;
			gbc_scrollPane_1.gridy = 2;
			panel.add(getScrollPane_1(), gbc_scrollPane_1);
			GridBagConstraints gbc_progressBar_1 = new GridBagConstraints();
			gbc_progressBar_1.insets = new Insets(0, 0, 0, 5);
			gbc_progressBar_1.fill = GridBagConstraints.BOTH;
			gbc_progressBar_1.gridwidth = 8;
			gbc_progressBar_1.gridx = 0;
			gbc_progressBar_1.gridy = 3;
			panel.add(getProgressBar_1(), gbc_progressBar_1);
		}
		return panel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Finestra di ricerca:");
		}
		return label;
	}
	private JSpinner getSpinner() {
		if (spinner == null) {
			spinner = new JSpinner();
		}
		return spinner;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Accuratezza ricerca:");
		}
		return label_1;
	}
	private JSlider getSlider() {
		if (slider == null) {
			slider = new JSlider();
			slider.setValue(85);
			slider.setSnapToTicks(true);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.setMinorTickSpacing(1);
			slider.setMinimum(70);
			slider.setMajorTickSpacing(5);
		}
		return slider;
	}
	private JButton getButton() {
		if (button == null) {
			button = new JButton("Analizza");
		}
		return button;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("Lunghezza minima delle parole:");
		}
		return label_2;
	}
	private JSpinner getSpinner_1() {
		if (spinner_1 == null) {
			spinner_1 = new JSpinner();
		}
		return spinner_1;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
		}
		return scrollPane_1;
	}
	private JProgressBar getProgressBar_1() {
		if (progressBar_1 == null) {
			progressBar_1 = new JProgressBar(0, 100);
			progressBar_1.setValue(0);
			progressBar_1.setStringPainted(true);
		}
		return progressBar_1;
	}
}
