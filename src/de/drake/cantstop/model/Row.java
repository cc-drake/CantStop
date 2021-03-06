package de.drake.cantstop.model;

/**
 * Eine Reihe, in der Fortschritte erziehlt werden k�nnen
 */
public class Row implements Comparable<Row> {

	/**
	 * Die Nummer der Reihe
	 */
	private final int number;

	/**
	 * Der gesicherte Fortschritt in der Reihe
	 */
	private int safeFortschritt = 0;
	
	/**
	 * Der aktuelle Fortschritt in der Reihe
	 */
	private int fortschritt = 0;
	
	/**
	 * Der maximal m�gliche Fortschritt in dieser Reihe
	 */
	private final int maxFortschritt;
	
	/**
	 * Gibt an, ob in dieser Reihe noch Fortschritt m�glich ist oder die Reihe bereits von einem Spieler abgeschlossen wurde
	 */
	private boolean isLocked = false;
	
	/**
	 * Erzeugt eine neue Reihe.
	 */
	Row(final int number, final int maxFortschritt) {
		this.number = number;
		this.maxFortschritt = maxFortschritt;
	}
	
	/**
	 * Erh�ht den Fortschritt bei dieser Reihe.
	 */
	void addFortschritt() {
		if (this.fortschritt < this.maxFortschritt)
			this.fortschritt = this.fortschritt + 1;
	}
	
	/**
	 * Erh�ht den Fortschritt bei dieser Reihe, sofern diese noch nicht abgeschlossen ist.
	 */
	void safeFortschritt() {
		this.safeFortschritt = this.fortschritt;
		if (this.fortschritt == this.maxFortschritt)
			this.isLocked = true;
	}
	
	/**
	 * Setzt den Fortschritt auf den gesicherten Fortschritt zur�ck.
	 */
	void resetFortschritt() {
		this.fortschritt = this.safeFortschritt;
	}
	
	/**
	 * Schlie�t die Reihe ab.
	 */
	void lock() {
		this.isLocked = true;
	}
	
	/**
	 * Setzt die Reihe auf den Ausgangszustand zur�ck.
	 */
	void reset() {
		this.safeFortschritt = 0;
		this.fortschritt = 0;
		this.isLocked = false;
	}
	
	/**
	 * Gibt eine Bewertungszahl zur�ck, die einen einfachen Fortschritt auf eine bestimmte H�he repr�sentiert.
	 * Aktuell: Die Summe aller H�hen ist f�r jede Reihe stets 0,5.
	 */
	private double getRating(final int h�he) {
		return ((double) h�he) / ((double) (this.maxFortschritt * (this.maxFortschritt + 1)));
	}
	
	/**
	 * Gibt eine Bewertungszahl zur�ck, die den aktuellen Gesamtfortschritt auf dieser Reihe bemisst.
	 */
	double getCurrentRating() {
		double result = 0.;
		for (int h�he = this.safeFortschritt + 1; h�he <= this.fortschritt; h�he++) {
			result += this.getRating(h�he);
		}
		return result;
	}
	
	/**
	 * Gibt eine Bewertungszahl zur�ck, die einen m�glichen Fortschritt um ein weiteres Feld auf dieser Reihe bemisst.
	 */
	double getNextRating() {
		return this.getRating(this.fortschritt + 1);
	}
	
	/**
	 * Gibt eine Bewertungszahl zur�ck, die einen m�glichen Fortschritt um zwei weitere Felder auf dieser Reihe bemisst.
	 */
	double getDoubleRating() {
		return this.getRating(this.fortschritt + 2);
	}
	
	/**
	 * Gibt die Nummer der Reihe zur�ck.
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * Gibt den aktuellen Fortschritt bei dieser Reihe zur�ck.
	 */
	public int getSafeFortschritt() {
		return this.safeFortschritt;
	}
	
	/**
	 * Gibt den aktuellen Fortschritt bei dieser Reihe zur�ck.
	 */
	public int getFortschritt() {
		return this.fortschritt;
	}
	
	/**
	 * Gibt den maximal m�glichen Fortschritt bei dieser Reihe zur�ck.
	 */
	public int getMaxFortschritt() {
		return this.maxFortschritt;
	}
	
	/**
	 * Gibt zur�ck, ob diese Reihe bereits abgeschlossen ist.
	 */
	public boolean isLocked() {
		return this.isLocked;
	}
	
	@Override
	public int hashCode() {
		return this.number;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Row other = (Row) obj;
		if (this.number != other.number)
			return false;
		return true;
	}

	@Override
	public int compareTo(final Row row2) {
		return this.number - row2.number;
	}
	
	@Override
	public String toString() {
		return "[Number = " + this.number + "]";
	}
	
}