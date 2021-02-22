package de.drake.cantstop;

import de.drake.cantstop.ki.AI;
import de.drake.cantstop.model.Game;
import de.drake.cantstop.model.Probability;
import de.drake.cantstop.model.RowManager;
import de.drake.cantstop.model.WurfManager;
import de.drake.cantstop.view.UI;

public class Launcher {
	
	/**
	 * Startet das Programm.
	 * 
	 * @param args
	 * 		wird ignoriert
	 */
	public static void main(String[] args) {
		RowManager.init();
		WurfManager.init();
		new Game();
		new Probability();
		UI ui = new UI();
		new AI(ui);
	}
	
}