package de.drake.cantstop.view;

import java.awt.Color;

import de.drake.cantstop.model.Row;
import de.drake.cantstop.view.util.BorderedLabel;

class Feld extends BorderedLabel {
	
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private final Row row;
	
	private final int h�he;

	Feld(final Row row, final int h�he) {
		super("");
		this.row = row;
		this.h�he = h�he;
		super.setOpaque(true);
	}
	
	void update() {
		if (this.h�he > this.row.getMaxFortschritt()) {
			this.setBackground(Color.GRAY);
		} else if (this.row.isLocked() && this.row.getSafeFortschritt() == this.row.getMaxFortschritt()) {
			this.setBackground(Color.BLUE);
		} else if (this.row.isLocked() && this.row.getSafeFortschritt() != this.row.getMaxFortschritt()){
			this.setBackground(Color.RED);
		} else if (this.h�he == this.row.getSafeFortschritt()){
			this.setBackground(Color.BLUE);
		} else if (this.h�he == this.row.getFortschritt()){
			this.setBackground(Color.BLACK);
		} else {
			this.setBackground(Color.WHITE);
		}
	}
	
}