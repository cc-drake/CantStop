package de.drake.cantstop.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.util.Pair;

public class WurfManager {
	
	public final static double ANZAHL_UNSORTIERTER_W�RFE = Math.pow(6, 4);
	
	/**
	 * Eine Liste aller m�glichen sortierten W�rfelw�rfe
	 */
	private final static ArrayList<Wurf> alleW�rfe = new ArrayList<Wurf>(126);
	
	/**
	 * Eine Grundgesamtheit aller unsortieren W�rfe zum zuf�lligen W�rfeln.
	 */
	private final static ArrayList<Wurf> grundgesamtheit_unsortierter_w�rfe = new ArrayList<Wurf>(1296);
	
	public static void init() {
		for (int w1 = 1; w1 < 7; w1++) {
			for (int w2 = 1; w2 < 7; w2++) {
				for (int w3 = 1; w3 < 7; w3++) {
					for (int w4 = 1; w4 < 7; w4++) {
						int[] unsortierterWurf = {w1, w2, w3, w4};
						Arrays.sort(unsortierterWurf);
						Wurf wurf = new Wurf(unsortierterWurf, 1);
						int index = WurfManager.alleW�rfe.indexOf(wurf);
						if (index == -1) {
							WurfManager.alleW�rfe.add(wurf);
						} else {
							WurfManager.alleW�rfe.get(index).addKombination();
						}
					}
				}
			}
		}
		
		for (Wurf wurf : WurfManager.alleW�rfe) {
			for (int i = 0; i < wurf.getKombinationen(); i++) {
				WurfManager.grundgesamtheit_unsortierter_w�rfe.add(wurf);
			}
		}
	}
	
	public static ArrayList<Wurf> getAlleW�rfe() {
		return WurfManager.alleW�rfe;
	}
	
	public static Wurf generateWurf() {
		Random random = new Random();
		return WurfManager.grundgesamtheit_unsortierter_w�rfe.get(random.nextInt(WurfManager.grundgesamtheit_unsortierter_w�rfe.size()));
	}
	
	/**
	 * Gibt die beste m�gliche Option des Wurfs sowie das zugeh�rige Row-Rating eines Wurfs zur�ck, die beim derzeitigen Spielstand m�glich ist.
	 */
	public static Pair<ArrayList<Row>, Double> getBestOption(final Wurf wurf) {
		ArrayList<Row> bestOption = null;
		Double bestRating = 0.;
		Game game = Game.getInstance();
		for (ArrayList<Row> option : wurf.getOptionen()) {
			Row option1 = option.get(0);
			Row option2 = option.get(1);
			if (option1 == option2 && game.canDoubleAdvance(option1) && option1.getDoubleRating() > bestRating) {
				bestRating = option1.getDoubleRating();
				bestOption = new ArrayList<Row>(2);
				bestOption.add(option1);
				bestOption.add(option2);
				continue;
			}
			if (option1 != option2 && game.canPairAdvance(option1, option2) && option1.getNextRating() + option2.getNextRating() > bestRating) {
				bestRating = option1.getNextRating() + option2.getNextRating();
				bestOption = new ArrayList<Row>(2);
				bestOption.add(option1);
				bestOption.add(option2);
				continue;
			}
			if (game.canAdvance(option1) && option1.getNextRating() > bestRating) {
				bestRating = option1.getNextRating();
				bestOption = new ArrayList<Row>(1);
				bestOption.add(option1);
			}
			if (game.canAdvance(option2) && option2.getNextRating() > bestRating) {
				bestRating = option2.getNextRating();
				bestOption = new ArrayList<Row>(1);
				bestOption.add(option2);
			}
		}
		return new Pair<ArrayList<Row>, Double>(bestOption, bestRating);
	}
	
}