package it.holls.writersutilityM.ui;

import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.SwingWorker;

public class Task extends SwingWorker<Void, Void> {

	private GUIold gui;

	public Task(GUIold gui) {
		this.gui = gui;
	}

	public Task(GUIold gui, PropertyChangeListener listener) {
		this.gui = gui;
		this.addPropertyChangeListener(listener);
	}

	/*
	 * Main task. Executed in background thread.
	 */
	@Override
	public Void doInBackground() {
		int progress = 0;
		// Initialize progress property.
		setProgress(0);
		while (progress < 100) {
			// Make random progress.
			progress += UIUtility.progress;
			setProgress(Math.min(progress, 100));
		}
		return null;
	}

	/*
	 * Executed in event dispatching thread
	 */
	@Override
	public void done() {
		Toolkit.getDefaultToolkit().beep();
		gui.getBtnAnalizza().setEnabled(true);
		gui.getBtnAnalizza().setCursor(null); // turn off the wait cursor
	}
	
	@Override
	protected void process(List<Void> chunks) {
		super.process(chunks);
	}
}
