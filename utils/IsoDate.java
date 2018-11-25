package nl.yasmijn.utils;

public class IsoDate implements Comparable<IsoDate> {
	private int year;
	private int week;
	private int day;

	public IsoDate(int year, int week, int day) {
		this.year = year;
		this.week = week;
		this.day = day;
	}

	public IsoDate(int d) {
		year = new SmallDate(d - 3).getYear();
		if (d >= new IsoDate(year + 1, Month.JANUARY.getNumber(), WeekDay.MONDAY.getNumber()).getDayNumber()) {
			++year;
		}
		day = d % 7;
		if (day == 0)
			day = 7;
		week = (d - new IsoDate(year, Month.JANUARY.getNumber(), WeekDay.MONDAY.getNumber()).getDayNumber()) / 7 + 1;
	}

	public IsoDate(SmallDate sd) {
		this(sd.getDayNumber());
	}

	private int getYearWeekDay() {
		return year * 1000 + week * 100 + day;
	}

	public int getYear() {
		return year;
	}

	public int getWeek() {
		return week;
	}

	public int getDay() {
		return day;
	}

	public int getDayNumber() {
		return xDayOnOrBefore(new SmallDate(year, 1, 4).getDayNumber(), 1) + 7 * (week - 1) + (day - 1);
	}

	private int xDayOnOrBefore(int d, int dow) {
		return (d - ((d - dow) % 7));
	}

	public int compareTo(IsoDate rhs) {
		return this.getYearWeekDay() - rhs.getYearWeekDay();
	}

	public String toString() {
		String weeknr = Integer.toString(getYearWeekDay());
		return weeknr.substring(0, 4) + "-W" + weeknr.substring(4, 6) + "-" + weeknr.substring(6);
	}
}
