package de.drake.cantstop.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Ein sortierter W�rfelwurf, d.h. die W�rfel sind aufsteigend geordnet.
 */
public class Wurf {
	
	/**
	 * Die W�rfel des Wurfs
	 */
	private final int[] w�rfel;
	
	/**
	 * Die Anzahl der unsortierten W�rfe, die zu diesem sortierten Wurf geh�ren
	 */
	private int kombinationen;
	
	/**
	 * Alle Optionen, die einem bei dem Wurf zur Verf�gung stehen (z.B. "5 und 9", "6 und 8", "7 und 7")
	 */
	private final HashSet<ArrayList<Row>> optionen = new HashSet<ArrayList<Row>>(3);
	
	/**
	 * Erzeugt einen Wurf mit den gegebenen W�rfeln und initialer Anzahl von Kombinationen.
	 */
	Wurf(final int[] w�rfel, final int kombinationen) {
		this.w�rfel = w�rfel;
		this.kombinationen = kombinationen;
		this.initOptionen();
	}
	
	/**
	 * Berechnet initial alle Optionen, die einem bei dem Wurf zur Verf�gung stehen (z.B. "5 und 9", "6 und 8", "7 und 7")
	 */
	private void initOptionen() {
		ArrayList<Row> option1 = new ArrayList<Row>(2);
		option1.add(RowManager.getRow(this.w�rfel[0] + this.w�rfel[1]));
		option1.add(RowManager.getRow(this.w�rfel[2] + this.w�rfel[3]));
		Collections.sort(option1);
		
		ArrayList<Row> option2 = new ArrayList<Row>(2);
		option2.add(RowManager.getRow(this.w�rfel[0] + this.w�rfel[2]));
		option2.add(RowManager.getRow(this.w�rfel[1] + this.w�rfel[3]));
		Collections.sort(option2);
		
		ArrayList<Row> option3 = new ArrayList<Row>(2);
		option3.add(RowManager.getRow(this.w�rfel[0] + this.w�rfel[3]));
		option3.add(RowManager.getRow(this.w�rfel[1] + this.w�rfel[2]));
		Collections.sort(option3);
		
		this.optionen.add(option1);
		this.optionen.add(option2);
		this.optionen.add(option3);
	}
	
	/**
	 * Erh�ht die Zahl der Kombinationen um 1.
	 */
	void addKombination() {
		this.kombinationen = this.kombinationen + 1;
	}
	
	/**
	 * Gibt die Zahl der unsortierten W�rfe zur�ck, die zu diesem sortierten Wurf geh�ren.
	 */
	int getKombinationen() {
		return this.kombinationen;
	}
	
	/**
	 * Gibt die Wahrscheinlichkeit zur�ck, mit der der Wurf gew�rfelt wird.
	 */
	double getProb() {
		return ((double) this.kombinationen) / WurfManager.ANZAHL_UNSORTIERTER_W�RFE;
	}
	
	/**
	 * Gibt alle verschiedenen Optionen zur�ck, die einem bei dem Wurf zur Verf�gung stehen (z.B. "5 und 9", "6 und 8", "7 und 7")
	 */
	HashSet<ArrayList<Row>> getOptionen() {
		return this.optionen;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result += this.w�rfel[i] * ((int) Math.pow(10, 3 - i));
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
		return Arrays.equals(w�rfel, other.w�rfel);
	}

	@Override
	public String toString() {
		return "[W�rfel=" + Arrays.toString(w�rfel) + "]";
	}
	
}