package de.drake.cantstop.model;

import java.util.Collection;
import java.util.HashMap;

public class RowManager {
	
	/**
	 * Die Reihen von 2 bis 12.
	 */
	private final static HashMap<Integer, Row> rows = new HashMap<Integer, Row>();
	
	public static void init() {
		RowManager.rows.put(2, new Row(2, 3));
		RowManager.rows.put(3, new Row(3, 5));
		RowManager.rows.put(4, new Row(4, 7));
		RowManager.rows.put(5, new Row(5, 9));
		RowManager.rows.put(6, new Row(6, 11));
		RowManager.rows.put(7, new Row(7, 13));
		RowManager.rows.put(8, new Row(8, 11));
		RowManager.rows.put(9, new Row(9, 9));
		RowManager.rows.put(10, new Row(10, 7));
		RowManager.rows.put(11, new Row(11, 5));
		RowManager.rows.put(12, new Row(12, 3));
	}
	
	public static Collection<Row> getAllRows() {
		return RowManager.rows.values();
	}
	
	public static Row getRow(final Integer number) {
		return RowManager.rows.get(number);
	}
	
}