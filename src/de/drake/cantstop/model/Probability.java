package de.drake.cantstop.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Ein Modul für die Wahrscheinlichkeitsberechnung des aktuellen Spiels.
 */
public class Probability {
	
	/**
	 * Die SinglePattern-Instance von Probability.
	 */
	private static Probability instance;
	
	/**
	 * Gibt die aktuelle Instanz von Probability zurück.
	 */
	public static Probability getInstance() {
		return Probability.instance;
	}
	
	/**
	 * Die Einzelhäufigkeiten, also die Anzahl günstiger, unsortierter Würfe für mindestens einen Fortschritt in der angegebenen Reihe.
	 * Muss nur inital berechnet werden, da diese nicht vom Spielstand abhängen.
	 */
	private final HashMap<Row, Integer> einzelhäufigkeiten = new HashMap<Row, Integer>(11);
	
	/**
	 * Die Anzahl günstiger, unsortierter Würfe, bei denen man keinen Fortschritt hat und "fizzlet".
	 */
	private int fizzle = 0;
	
	/**
	 * Eine Liste aller möglichen sortierten Würfelwürfe
	 */
	private final ArrayList<Wurf> alleWürfe = new ArrayList<Wurf>(126);
	
	/**
	 * Erzeugt eine neues Probabilitymodul für das genannte Spiel.
	 */
	public Probability() {
		Probability.instance = this;
		this.initWürfe();
		this.initEinzelprobs();
	}
	
	/**
	 * Initialisiert die möglichen sortierten Würfe.
	 */
	private void initWürfe() {
		for (int w1 = 1; w1 < 7; w1++) {
			for (int w2 = 1; w2 < 7; w2++) {
				for (int w3 = 1; w3 < 7; w3++) {
					for (int w4 = 1; w4 < 7; w4++) {
						int[] unsortierterWurf = {w1, w2, w3, w4};
						Arrays.sort(unsortierterWurf);
						Wurf wurf = new Wurf(unsortierterWurf, 1);
						int index = alleWürfe.indexOf(wurf);
						if (index == -1) {
							alleWürfe.add(wurf);
						} else {
							alleWürfe.get(index).addKombination();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Berechnet initial die Einzelwahrscheinlichkeiten.
	 */
	private void initEinzelprobs() {
		for (Wurf wurf : this.alleWürfe) {
			for (Row row : RowManager.getAllRows()) {
				for (ArrayList<Row> option : wurf.getOptionen()) {
					if (option.contains(row)) {
						int bisherigeHäufigkeit = this.einzelhäufigkeiten.containsKey(row) ? this.einzelhäufigkeiten.get(row) : 0;
						this.einzelhäufigkeiten.put(row, bisherigeHäufigkeit + wurf.getKombinationen());
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Berechnet alle Wahrscheinlichkeiten neu.
	 */
	public void calc() {
		Game game = Game.getInstance();
		
		this.fizzle = 0;
		
		for (Wurf wurf : this.alleWürfe) {
			boolean fizzlet = true;
			for (ArrayList<Row> option : wurf.getOptionen()) {
				if (game.canAdvance(option.get(0)) || game.canAdvance(option.get(1))) {
					fizzlet = false;
					break;
				}
			}
			if (fizzlet) {
				this.fizzle += wurf.getKombinationen();
			}
		}
	}
	
	public double getEinzelwahrscheinlichkeit(final Row row) {
		if (!Game.getInstance().canAdvance(row))
			return 0.;
		return this.einzelhäufigkeiten.get(row) / Wurf.ANZAHL_UNSORTIERTER_WÜRFE;
	}
	
	public double getFizzleWahrscheinlichkeit() {
		return this.fizzle / Wurf.ANZAHL_UNSORTIERTER_WÜRFE;
	}
	
}