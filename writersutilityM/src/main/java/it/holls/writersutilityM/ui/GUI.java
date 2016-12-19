package it.holls.writersutilityM.ui;

import java.awt.EventQueue;
import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.apache.poi.xwpf.converter.core.XWPFConverterException;

import it.holls.writersutility.observer.GUIObserver;
import it.holls.writersutilityM.documentReader.DocumentReader;
import it.holls.writersutilityM.documentReader.DocumentReaderInLineText;
import it.holls.writersutilityM.documentReader.FactoryDocumentReader;
import it.holls.writersutilityM.utils.Utils;
import it.holls.writersutility_plugin.plugin.Plugin;
import it.holls.writersutility_plugin.service.LoaderService;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class GUI extends GUIObserver {

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
	
//	private Task task;
	private JTabbedPane tabbedPane;
	private JScrollPane scrollPane;
	private JEditorPane editorPane;
	private GroupLayout groupLayout;
	
	private LoaderService loaderService;
	
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
		this.loaderService = LoaderService.getInstance();
		this.loaderService.addObserver(this);
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
			List<Plugin> pluginList = loaderService.getPlugins();
			System.out.println("Loading plugins..."+pluginList.size());
			for(Plugin plugin : pluginList){
				System.out.println("Loaded: "+plugin.getName());
				tabbedPane.addTab(plugin.getName(), null, plugin.getPanel(), null);
			}
			
//			JPanel pippo = getPanelRipetizioni();
//			pippo.setBounds(0, 0, 110, 110);
//			tabbedPane.addTab("Ripetizioni", null, getPanelRipetizioni(), null);
//			tabbedPane.addTab("Errori", null, getPanelErrori(), null);
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
	
	public String getOriginalText(){
		if(!fileLoaded){
			try {
				text = checkAndConvertToHTML(editorPane.getDocument().getText(0, editorPane.getDocument().getLength()));
				text = Utils.fixHtml(text);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return text;
	}
	
	public void setOriginalText(String text){
		this.text = text;
		this.setText(text);
	}
	
	private String getText() {
		return fileLoaded ? newText : Utils.fixHtml(checkAndConvertToHTML(editorPane.getText()));
	}
	
	private void setText(String text){
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

	public void setFileLoaded(boolean loaded){
		this.fileLoaded = loaded;
	}

	@Override
	public void setNewText(String newText) {
		this.setText(newText);
	}

	@Override
	public String getGUIText() {
		return this.getText();
	}
	
	private String checkAndConvertToHTML(String inputText){
		if(inputText.startsWith("<html>") || inputText.startsWith("<!DOCTYPE"))
			return inputText;
		else
			return Utils.createHtmlFromText(inputText);
	}
	
}
