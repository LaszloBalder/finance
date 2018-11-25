package nl.yasmijn.utils;

public enum Month {
	JANUARY(1),
	FEBRUARY(2),
	MARCH(3),
	APRIL(4),
	MAY(5),
	JUNE(6),
	JULY(7),
	AUGUST(8),
	SEPTEMBER(9),
	OKTOBER(10),
	NOVEMBER(11),
	DECEMBER(12);
	
	private final int monthNumber;
	Month(int monthNumber) {
		this.monthNumber = monthNumber;
	}
	
	public int getNumber() {
		return monthNumber;
	}
	
	public static void main(String[] args) {
		for (Month m : Month.values()) {
			System.out.printf("Maand %s heeft maandnummer %d\n", m, m.getNumber());
		}
	}
}
