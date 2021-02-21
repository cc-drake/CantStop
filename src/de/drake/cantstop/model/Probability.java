package de.drake.cantstop.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Ein Modul f�r die Wahrscheinlichkeitsberechnung des aktuellen Spiels.
 */
public class Probability {
	
	/**
	 * Die SinglePattern-Instance von Probability.
	 */
	private static Probability instance;
	
	/**
	 * Gibt die aktuelle Instanz von Probability zur�ck.
	 */
	public static Probability getInstance() {
		return Probability.instance;
	}
	
	/**
	 * Die Einzelh�ufigkeiten, also die Anzahl g�nstiger, unsortierter W�rfe f�r mindestens einen Fortschritt in der angegebenen Reihe.
	 * Muss nur inital berechnet werden, da diese nicht vom Spielstand abh�ngen.
	 */
	private final HashMap<Row, Integer> einzelh�ufigkeiten = new HashMap<Row, Integer>(11);
	
	/**
	 * Die Anzahl g�nstiger, unsortierter W�rfe, bei denen man keinen Fortschritt hat und "fizzlet".
	 */
	private int fizzle = 0;
	
	/**
	 * Eine Liste aller m�glichen sortierten W�rfelw�rfe
	 */
	private final ArrayList<Wurf> alleW�rfe = new ArrayList<Wurf>(126);
	
	/**
	 * Erzeugt eine neues Probabilitymodul f�r das genannte Spiel.
	 */
	public Probability() {
		Probability.instance = this;
		this.initW�rfe();
		this.initEinzelprobs();
	}
	
	/**
	 * Initialisiert die m�glichen sortierten W�rfe.
	 */
	private void initW�rfe() {
		for (int w1 = 1; w1 < 7; w1++) {
			for (int w2 = 1; w2 < 7; w2++) {
				for (int w3 = 1; w3 < 7; w3++) {
					for (int w4 = 1; w4 < 7; w4++) {
						int[] unsortierterWurf = {w1, w2, w3, w4};
						Arrays.sort(unsortierterWurf);
						Wurf wurf = new Wurf(unsortierterWurf, 1);
						int index = alleW�rfe.indexOf(wurf);
						if (index == -1) {
							alleW�rfe.add(wurf);
						} else {
							alleW�rfe.get(index).addKombination();
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
		for (Wurf wurf : this.alleW�rfe) {
			for (Row row : RowManager.getAllRows()) {
				for (ArrayList<Row> option : wurf.getOptionen()) {
					if (option.contains(row)) {
						int bisherigeH�ufigkeit = this.einzelh�ufigkeiten.containsKey(row) ? this.einzelh�ufigkeiten.get(row) : 0;
						this.einzelh�ufigkeiten.put(row, bisherigeH�ufigkeit + wurf.getKombinationen());
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
		
		for (Wurf wurf : this.alleW�rfe) {
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
		return this.einzelh�ufigkeiten.get(row) / Wurf.ANZAHL_UNSORTIERTER_W�RFE;
	}
	
	public double getFizzleWahrscheinlichkeit() {
		return this.fizzle / Wurf.ANZAHL_UNSORTIERTER_W�RFE;
	}
	
}