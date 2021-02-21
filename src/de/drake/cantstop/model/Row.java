package de.drake.cantstop.model;

/**
 * Eine Reihe, in der Fortschritte erziehlt werden können
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
	 * Der maximal mögliche Fortschritt in dieser Reihe
	 */
	private final int maxFortschritt;
	
	/**
	 * Gibt an, ob in dieser Reihe noch Fortschritt möglich ist oder die Reihe bereits von einem Spieler abgeschlossen wurde
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
	 * Erhöht den Fortschritt bei dieser Reihe.
	 */
	void addFortschritt() {
		if (this.fortschritt < this.maxFortschritt)
			this.fortschritt = this.fortschritt + 1;
	}
	
	/**
	 * Erhöht den Fortschritt bei dieser Reihe, sofern diese noch nicht abgeschlossen ist.
	 */
	void safeFortschritt() {
		this.safeFortschritt = this.fortschritt;
		if (this.fortschritt == this.maxFortschritt)
			this.isLocked = true;
	}
	
	/**
	 * Setzt den Fortschritt auf den gesicherten Fortschritt zurück.
	 */
	void resetFortschritt() {
		this.fortschritt = this.safeFortschritt;
	}
	
	/**
	 * Schließt die Reihe ab.
	 */
	void lock() {
		this.isLocked = true;
	}
	
	/**
	 * Setzt die Reihe auf den Ausgangszustand zurück.
	 */
	void reset() {
		this.safeFortschritt = 0;
		this.fortschritt = 0;
		this.isLocked = false;
	}
	
	/**
	 * Gibt die Nummer der Reihe zurück.
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * Gibt den aktuellen Fortschritt bei dieser Reihe zurück.
	 */
	public int getSafeFortschritt() {
		return this.safeFortschritt;
	}
	
	/**
	 * Gibt den aktuellen Fortschritt bei dieser Reihe zurück.
	 */
	public int getFortschritt() {
		return this.fortschritt;
	}
	
	/**
	 * Gibt den maximal möglichen Fortschritt bei dieser Reihe zurück.
	 */
	public int getMaxFortschritt() {
		return this.maxFortschritt;
	}
	
	/**
	 * Gibt zurück, ob diese Reihe bereits abgeschlossen ist.
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