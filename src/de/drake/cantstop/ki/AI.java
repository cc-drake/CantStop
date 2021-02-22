package de.drake.cantstop.ki;

import java.util.ArrayList;

import de.drake.cantstop.model.Game;
import de.drake.cantstop.model.Probability;
import de.drake.cantstop.model.Row;
import de.drake.cantstop.model.Wurf;
import de.drake.cantstop.model.WurfManager;
import de.drake.cantstop.view.UI;

public class AI {
	
	/**
	 * Erzeugt eine neue AI zu dem angegebenen Spiel bzw. der angegebenen Probability.
	 */
	public AI(final UI ui) {
		Game game = Game.getInstance();
		Integer zahlDerWürfe = 0;
		
		while(zahlDerWürfe < 1000) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ui.update();
			zahlDerWürfe++;
			Wurf wurf = WurfManager.generateWurf();
			System.out.println(wurf);
			ArrayList<Row> besteOption = WurfManager.getBestOption(wurf).getKey();
			System.out.println(besteOption);
			if (besteOption == null) {
				System.out.println("fail");
				game.fail();
				continue;
			} else {
				for (Row row : besteOption) {
					Game.getInstance().addFortschritt(row);
				}
				if (game.getFullRows() == 3) {
					System.out.println("Sieg!");
					this.protokolliere(zahlDerWürfe.toString());
					zahlDerWürfe = 0;
					game.restart();
					continue;
				}
				if (this.weitermachenLohntSich()) {
					System.out.println("fail");
					continue;
				}
				game.stop();
				continue;
			}
		}
	}
	
	private void protokolliere(final String text) {
		System.out.println(text);
	}
	
	private boolean weitermachenLohntSich() {
		Probability prob = Probability.getInstance();
		return (prob.getExpectedRating() > prob.getCurrentRating());
	}
	
}