package de.drake.cantstop.view.util;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BorderedLabel extends JLabel {

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public BorderedLabel(final String title) {
		super(title, SwingConstants.CENTER);
		super.setPreferredSize(new Dimension(40, 20));
		super.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
}