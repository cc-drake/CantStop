package de.drake.cantstop.model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

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
	private int fizzleKombi;
	
	/**
	 * Der Erwartungswert f�r den Fortschritt nach einem weiteren Wurf (ohne Teilen durch Wurf.ANZAHL_UNSORTIERTER_W�RFE)
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
		for (Wurf wurf : WurfManager.getAlleW�rfe()) {
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
		this.fizzleKombi = 0;
		this.expectedRatingKombi = 0.;
		
		for (Wurf wurf : WurfManager.getAlleW�rfe()) {
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
		return this.einzelh�ufigkeiten.get(row) / WurfManager.ANZAHL_UNSORTIERTER_W�RFE;
	}
	
	public double getFizzleWahrscheinlichkeit() {
		return this.fizzleKombi / WurfManager.ANZAHL_UNSORTIERTER_W�RFE;
	}
	
	/**
	 * Gibt eine Bewertungszahl zur�ck, die den aktuellen Fortschritt bemisst.
	 */
	public double getCurrentRating() {
		double result = 0.;
		for (Row row : Game.getInstance().getActiveRows()) {
			result += row.getCurrentRating();
		}
		return result;
	}
	
	/**
	 * Gibt den Erwartungswert f�r den Fortschritt nach einem weiteren Wurf zur�ck.
	 */
	public double getExpectedRating() {
		return (this.getCurrentRating() * (WurfManager.ANZAHL_UNSORTIERTER_W�RFE - this.fizzleKombi) + this.expectedRatingKombi) / WurfManager.ANZAHL_UNSORTIERTER_W�RFE;
	}
	
}