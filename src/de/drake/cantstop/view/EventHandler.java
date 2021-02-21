package de.drake.cantstop.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.drake.cantstop.model.Game;
import de.drake.cantstop.model.Probability;

class EventHandler implements ActionListener {
	
	final static String RESTART = "Neustart";
	
	final static String STOP = "Stopp";
	
	final static String FAIL = "Verloren";
	
	/**
	 * Die Ui, die bei den Events aktualisiert werden muss
	 */
	private final UI ui;
	
	/**
	 * Erzeugt einen neuen RowEventHandler.
	 */
	EventHandler(final UI ui) {
		this.ui = ui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case EventHandler.RESTART:
			Game.getInstance().restart();
			break;
		case EventHandler.STOP:
			Game.getInstance().stop();
			break;
		case EventHandler.FAIL:
			Game.getInstance().fail();
			break;
		}
		Probability.getInstance().calc();
		this.ui.update();
	}

}