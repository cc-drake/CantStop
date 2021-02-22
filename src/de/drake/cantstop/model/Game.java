package de.drake.cantstop.model;

import java.util.HashSet;

/**
 * Das Spiel, in dem die aktuellen Fortschritte und Wahrscheinlichkeiten abgebildet werden
 */
public class Game {
	
	/**
	 * Die SinglePattern-Instance von Game.
	 */
	private static Game instance;
	
	/**
	 * Gibt die aktuelle Instanz von Game zurück.
	 */
	public static Game getInstance() {
		return Game.instance;
	}
	
	/**
	 * Die Menge der fertigen Rows.
	 */
	private int fullRows = 0;
	
	/**
	 * Die Reihen, in denen in der jetztigen Runde Fortschritte erzielt wurden
	 */
	private final HashSet<Row> activeRows = new HashSet<Row>(3);
	
	/**
	 * Erzeugt eine neue Instanz von Game.
	 */
	public Game() {
		Game.instance = this;
	}
	
	/**
	 * Setzt den Spielfortschritt zurück.
	 */
	public void restart() {
		this.activeRows.clear();
		for (Row row : RowManager.getAllRows()) {
			row.reset();
		}
	}
	
	/**
	 * Beendet den Zug. Der erreichte Fortschritt wird hierbei gesichert.
	 */
	public void stop() {
		for (Row row : this.activeRows) {
			row.safeFortschritt();
			if (row.isLocked())
				this.fullRows++;
		}
		this.activeRows.clear();
	}
	
	/**
	 * Verliert den gesamten Fortschritt der Runde.
	 */
	public void fail() {
		for (Row row : this.activeRows) {
			row.resetFortschritt();
		}
		this.activeRows.clear();
	}
	
	/**
	 * Erhöht den Fortschritt bei der angegebenen Reihe.
	 */
	public void addFortschritt(final Row row) {
		if (!this.canAdvance(row))
			return;
		if (!this.activeRows.contains(row)) {
			this.activeRows.add(row);
		}
		row.addFortschritt();
	}
	
	/**
	 * Verschließt die angegebene Reihe.
	 */
	public void lock(final Row row) {
		if (!this.activeRows.contains(row))
			row.lock();
	}
	
	/**
	 * Gibt an, ob in der ausgewählten Reihe ein Fortschritt möglich ist.
	 */
	public boolean canAdvance(final Row row) {
		if (row.isLocked())
			return false;
		if (row.getFortschritt() == row.getMaxFortschritt())
			return false;
		if (this.activeRows.contains(row) || this.activeRows.size() < 3)
			return true;
		return false;
	}
	
	/**
	 * Gibt an, ob in der ausgewählten Reihe zwei Fortschritte möglich sind.
	 */
	public boolean canDoubleAdvance(final Row row) {
		if (this.canAdvance(row) && row.getFortschritt() < row.getMaxFortschritt() - 1)
			return true;
		return false;
	}
	
	/**
	 * Gibt an, ob ein simultaner Fortschritt in zwei verschiedenen Reihen möglich ist.
	 */
	public boolean canPairAdvance(final Row option1, final Row option2) {
		if (!this.canAdvance(option1) || !this.canAdvance(option2))
			return false;
		if (this.activeRows.size() < 2 || this.activeRows.contains(option1) || this.activeRows.contains(option2))
			return true;
		return false;
	}
	
	/**
	 * Gibt die Reihen zurück, in denen in der jetztigen Runde Fortschritte erzielt wurden
	 */
	public HashSet<Row> getActiveRows() {
		return this.activeRows;
	}
	
	/**
	 * Gibt die Zahl der erfolgreich gefüllten Reihen zurück.
	 */
	public int getFullRows() {
		return this.fullRows;
	}
	
}