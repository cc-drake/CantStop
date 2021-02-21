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
	 * Die Reihen, in denen in der jetztigen Runde Fortschritte erzielt wurden
	 */
	private final HashSet<Row> activeRows = new HashSet<Row>(3);
	
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
		if (row.isLocked())
			return;
		if (!this.activeRows.contains(row)) {
			if (this.activeRows.size() == 3)
				return;
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
	boolean canAdvance(final Row row) {
		if (row.isLocked())
			return false;
		if (this.activeRows.contains(row) && row.getFortschritt() != row.getMaxFortschritt())
			return true;
		if (this.activeRows.size() < 3)
			return true;
		return false;
	}
	
}