package de.drake.cantstop.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.drake.cantstop.model.Probability;

/**
 * Die grafische Oberfläche des Programms.
 */
public class UI extends JFrame{
	
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Eine Liste aller rowPanels der UI.
	 */
	private final Spielfeld spielfeld = new Spielfeld(this);
	
	JLabel fizzle = new JLabel("");
	
	JLabel currentRating = new JLabel("");
	
	JLabel expectedRating = new JLabel("");
	
	/**
	 * Erzeugt eine neue UI zu dem angegebenen Spiel bzw. der angegebenen Probability.
	 */
	public UI() {
		super("Can't Stop");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		this.add(topPanel, BorderLayout.NORTH);
		
			JButton button_restart = new JButton("Neustart");
			button_restart.addActionListener(new EventHandler(this));
			button_restart.setActionCommand(EventHandler.RESTART);
			topPanel.add(button_restart);
		
		this.add(new JScrollPane(this.spielfeld), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);
		
			JButton button_stop = new JButton("Stopp");
			button_stop.addActionListener(new EventHandler(this));
			button_stop.setActionCommand(EventHandler.STOP);
			buttonPanel.add(button_stop);
			
			JButton button_fail = new JButton("Verloren");
			button_fail.addActionListener(new EventHandler(this));
			button_fail.setActionCommand(EventHandler.FAIL);
			buttonPanel.add(button_fail);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(infoPanel, BorderLayout.WEST);
		
			infoPanel.add(this.fizzle);
			infoPanel.add(this.currentRating);
			infoPanel.add(this.expectedRating);
		
		this.update();
		this.setVisible(true);
	}
	
	/**
	 * Aktualisiert die GUI.
	 */
	void update() {
		this.spielfeld.update();
		this.fizzle.setText("Fizzle-Chance: " + Math.round(Probability.getInstance().getFizzleWahrscheinlichkeit() * 1000.)/10. + "%");
		this.currentRating.setText("Aktueller Fortschritt: " + Math.round(Probability.getInstance().getCurrentRating() * 1000.)/10.);
		this.expectedRating.setText("Erwarteter Fortschritt: " + Math.round(Probability.getInstance().getExpectedRating() * 1000.)/10.);
	}
	
}