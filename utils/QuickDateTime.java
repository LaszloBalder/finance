package nl.yasmijn.utils;

public class QuickDateTime {
	private int minutes;
	private int dayNr;
	private int year;
	private int month;
	private int day;
	public QuickDateTime(int datetime){
		this.minutes = datetime % 1440;
	}
	
	
}
