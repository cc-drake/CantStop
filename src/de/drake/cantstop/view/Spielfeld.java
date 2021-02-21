package de.drake.cantstop.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.drake.cantstop.model.Probability;
import de.drake.cantstop.model.Row;
import de.drake.cantstop.model.RowManager;
import de.drake.cantstop.view.util.BorderedLabel;

class Spielfeld extends JPanel {

	/**
	 * Default serialVersionID
	 */
	private static final long serialVersionUID = 1L;
	
	private final HashSet<Feld> felder = new HashSet<Feld>();
	
	private HashMap<Row, BorderedLabel> wahrscheinlichkeiten = new HashMap<Row, BorderedLabel>(11);
	
	Spielfeld(final UI ui) {
		super.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(1, 1, 1, 1);
		
		for (Row row : RowManager.getAllRows()) {
			c.gridx = row.getNumber() - 2;
			
			c.gridy = 0;
			this.add(new BorderedLabel(Integer.toString(row.getNumber())), c);
			
			c.gridy = 1;
			BorderedLabel ws = new BorderedLabel("");
			this.wahrscheinlichkeiten.put(row, ws);
			this.add(ws, c);
			
			for (int fortschritt = 1; fortschritt <= 13; fortschritt++) {
				c.gridy = 15 - fortschritt;
				Feld feld = new Feld(row, fortschritt);
				this.felder.add(feld);
				this.add(feld, c);
			}
			
			c.gridy = 15;
			JButton button_fortschritt = new JButton("+");
			button_fortschritt.addActionListener(new RowEventHandler(ui, row));
			button_fortschritt.setActionCommand(RowEventHandler.FORTSCHRITT);
			this.add(button_fortschritt, c);
			
			c.gridy = 16;
			JButton button_lock = new JButton("X");
			button_lock.addActionListener(new RowEventHandler(ui, row));
			button_lock.setActionCommand(RowEventHandler.LOCK);
			this.add(button_lock, c);
		}
	}

	public void update() {
		for (Feld feld : this.felder)
			feld.update();
		for (Row row : RowManager.getAllRows()) {
			this.wahrscheinlichkeiten.get(row).setText("" + (int) Math.round(Probability.getInstance().getEinzelwahrscheinlichkeit(row) * 100) + "%");
		}
	}
	
}