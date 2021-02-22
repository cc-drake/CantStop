package de.drake.cantstop.model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

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
	private int fizzleKombi;
	
	/**
	 * Der Erwartungswert für den Fortschritt nach einem weiteren Wurf (ohne Teilen durch Wurf.ANZAHL_UNSORTIERTER_WÜRFE)
	 */
	private double expectedRatingKombi;
	
	/**
	 * Erzeugt eine neue Instanz von Probability.
	 */
	public Probability() {
		Probability.instance = this;
		this.initEinzelprobs();
		this.calc();
	}
	
	/**
	 * Berechnet initial die Einzelwahrscheinlichkeiten.
	 */
	private void initEinzelprobs() {
		for (Wurf wurf : WurfManager.getAlleWürfe()) {
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
		this.fizzleKombi = 0;
		this.expectedRatingKombi = 0.;
		
		for (Wurf wurf : WurfManager.getAlleWürfe()) {
			Pair<ArrayList<Row>, Double> bestOption = WurfManager.getBestOption(wurf);
			if (bestOption.getKey() == null) {
				this.fizzleKombi += wurf.getKombinationen();
			} else {
				this.expectedRatingKombi += bestOption.getValue() * wurf.getKombinationen();
			}
		}
	}
	
	public double getEinzelwahrscheinlichkeit(final Row row) {
		if (!Game.getInstance().canAdvance(row))
			return 0.;
		return this.einzelhäufigkeiten.get(row) / WurfManager.ANZAHL_UNSORTIERTER_WÜRFE;
	}
	
	public double getFizzleWahrscheinlichkeit() {
		return this.fizzleKombi / WurfManager.ANZAHL_UNSORTIERTER_WÜRFE;
	}
	
	/**
	 * Gibt eine Bewertungszahl zurück, die den aktuellen Fortschritt bemisst.
	 */
	public double getCurrentRating() {
		double result = 0.;
		for (Row row : Game.getInstance().getActiveRows()) {
			result += row.getCurrentRating();
		}
		return result;
	}
	
	/**
	 * Gibt den Erwartungswert für den Fortschritt nach einem weiteren Wurf zurück.
	 */
	public double getExpectedRating() {
		return (this.getCurrentRating() * (WurfManager.ANZAHL_UNSORTIERTER_WÜRFE - this.fizzleKombi) + this.expectedRatingKombi) / WurfManager.ANZAHL_UNSORTIERTER_WÜRFE;
	}
	
}