package de.drake.cantstop.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Ein sortierter Würfelwurf, d.h. die Würfel sind aufsteigend geordnet.
 */
public class Wurf {
	
	/**
	 * Die Würfel des Wurfs
	 */
	private final int[] würfel;
	
	/**
	 * Die Anzahl der unsortierten Würfe, die zu diesem sortierten Wurf gehören
	 */
	private int kombinationen;
	
	/**
	 * Alle Optionen, die einem bei dem Wurf zur Verfügung stehen (z.B. "5 und 9", "6 und 8", "7 und 7")
	 */
	private final HashSet<ArrayList<Row>> optionen = new HashSet<ArrayList<Row>>(3);
	
	/**
	 * Erzeugt einen Wurf mit den gegebenen Würfeln und initialer Anzahl von Kombinationen.
	 */
	Wurf(final int[] würfel, final int kombinationen) {
		this.würfel = würfel;
		this.kombinationen = kombinationen;
		this.initOptionen();
	}
	
	/**
	 * Berechnet initial alle Optionen, die einem bei dem Wurf zur Verfügung stehen (z.B. "5 und 9", "6 und 8", "7 und 7")
	 */
	private void initOptionen() {
		ArrayList<Row> option1 = new ArrayList<Row>(2);
		option1.add(RowManager.getRow(this.würfel[0] + this.würfel[1]));
		option1.add(RowManager.getRow(this.würfel[2] + this.würfel[3]));
		Collections.sort(option1);
		
		ArrayList<Row> option2 = new ArrayList<Row>(2);
		option2.add(RowManager.getRow(this.würfel[0] + this.würfel[2]));
		option2.add(RowManager.getRow(this.würfel[1] + this.würfel[3]));
		Collections.sort(option2);
		
		ArrayList<Row> option3 = new ArrayList<Row>(2);
		option3.add(RowManager.getRow(this.würfel[0] + this.würfel[3]));
		option3.add(RowManager.getRow(this.würfel[1] + this.würfel[2]));
		Collections.sort(option3);
		
		this.optionen.add(option1);
		this.optionen.add(option2);
		this.optionen.add(option3);
	}
	
	/**
	 * Erhöht die Zahl der Kombinationen um 1.
	 */
	void addKombination() {
		this.kombinationen = this.kombinationen + 1;
	}
	
	/**
	 * Gibt die Zahl der unsortierten Würfe zurück, die zu diesem sortierten Wurf gehören.
	 */
	int getKombinationen() {
		return this.kombinationen;
	}
	
	/**
	 * Gibt die Wahrscheinlichkeit zurück, mit der der Wurf gewürfelt wird.
	 */
	double getProb() {
		return ((double) this.kombinationen) / WurfManager.ANZAHL_UNSORTIERTER_WÜRFE;
	}
	
	/**
	 * Gibt alle verschiedenen Optionen zurück, die einem bei dem Wurf zur Verfügung stehen (z.B. "5 und 9", "6 und 8", "7 und 7")
	 */
	HashSet<ArrayList<Row>> getOptionen() {
		return this.optionen;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result += this.würfel[i] * ((int) Math.pow(10, 3 - i));
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wurf other = (Wurf) obj;
		return Arrays.equals(würfel, other.würfel);
	}

	@Override
	public String toString() {
		return "[Würfel=" + Arrays.toString(würfel) + "]";
	}
	
}