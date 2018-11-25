package nl.yasmijn.utils;

public enum WeekDay {
	MONDAY(1),
	TUESDAY(2),
	WEDNESDAY(3),
	THURSDAY(4),
	FRIDAY(5),
	SATURDAY(6),
	SUNDAY(7);
	
	private final int isoNumber;
	private WeekDay(int isoNumber) {
		this.isoNumber = isoNumber;
	}
	
	public int getNumber() {
		return isoNumber;
	}
	
	public static void main(String[] args) {
		for (WeekDay d : WeekDay.values()) {
			System.out.printf("Dag %s heeft dagnummer %d\n", d, d.getNumber());
		}
	}
}
