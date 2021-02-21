package de.drake.cantstop.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.drake.cantstop.model.Game;
import de.drake.cantstop.model.Probability;
import de.drake.cantstop.model.Row;

class RowEventHandler implements ActionListener {
	
	final static String FORTSCHRITT = "Fortschritt";
	
	final static String LOCK = "Verschlieﬂen";
	
	/**
	 * Die Ui, die bei den Events aktualisiert werden muss
	 */
	private final UI ui;
	
	/**
	 * Die Row, auf die sich die Events auswirken
	 */
	private final Row row;
	
	/**
	 * Erzeugt einen neuen RowEventHandler.
	 */
	RowEventHandler(final UI ui, final Row row) {
		this.ui = ui;
		this.row = row;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case RowEventHandler.FORTSCHRITT:
			Game.getInstance().addFortschritt(this.row);
			break;
		case RowEventHandler.LOCK:
			Game.getInstance().lock(this.row);
			break;
		}
		Probability.getInstance().calc();
		this.ui.update();
	}

}