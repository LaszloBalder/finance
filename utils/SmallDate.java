package nl.yasmijn.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SmallDate implements Comparable<SmallDate> {
	private static Calendar calendar = GregorianCalendar.getInstance();
	private int absoluteDay = 0;
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private static final int[] cummMonthEnd = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };
	private static final int[] daysInMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final char yearFormat = 'y';
	private static final char monthFormat = 'm';
	private static final char dayFormat = 'd';
	private static final int yearPosition = 0;
	private static final int monthPosition = 1;
	private static final int dayPosition = 2;
	private static final int datePartCount = 3;

	public SmallDate(int year, int month, int day) {
		if (!isValid(year, month, day))
			throw new IllegalArgumentException("Date is not valid");
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public SmallDate(int d) {
		absoluteDay = d;
		year = 10 * d / 3653;
		while (d >= getAbsoluteDay(year + 1, 1, 1)) {
			++year;
		}
		if (year > 9999) {
			throw new IllegalArgumentException("Year is more 9999");
		}
		int dayInYear = d - getAbsoluteDay(year, 1, 1) + 1;
		month = (dayInYear / 32) + 1;
		if (getMonthEndDay(year, month) < dayInYear) {
			++month;
		}
		day = d - getAbsoluteDay(year, month, 1) + 1;
	}

	public SmallDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DATE);
	}

	public static boolean isLeapYear(int year) {
		return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
	}

	private static int getMonthEndDay(int year, int month) {
		if (month > 1 && isLeapYear(year))
			return cummMonthEnd[month] + 1;
		else
			return cummMonthEnd[month];
	}

	private static int getLastDayOfMonth(int year, int month) {
		if (month == 2 && isLeapYear(year))
			return daysInMonth[month] + 1;
		else
			return daysInMonth[month];
	}

	private static int getDayInYear(int y, int m, int d) {
		return getMonthEndDay(y, m - 1) + d;
	}

	private static int getAbsoluteDay(int y, int m, int d) {
		return getDayInYear(y, m, d) + (365 * (y - 1)) + ((y - 1) / 4) - ((y - 1) / 100) + ((y - 1) / 400);
	}

	public static SmallDate today() {
		return new SmallDate(new Date());
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public boolean isLastDayOfMonth() {
		return day == getLastDayOfMonth(year, month);
	}

	public SmallDate addDays(int days) {
		if (isValid(year, month, day + days)) {
			return new SmallDate(year, month, day + days);
		} else {
			return new SmallDate(getDayNumber() + days);
		}
	}

	public SmallDate addMonths(int months) {
		int totalMonths = 12 * year + (month - 1) + months;
		int newMonth = (totalMonths % 12) + 1;
		int newYear = totalMonths / 12;
		int newDay = Math.min(day, getLastDayOfMonth(newYear, newMonth));
		return new SmallDate(newYear, newMonth, newDay);
	}

	public SmallDate addYears(int years) {
		int newDay = Math.min(day, getLastDayOfMonth(year + years, month));
		return new SmallDate(year + years, month, newDay);
	}

	public int getDayNumber() {
		if (absoluteDay == 0) {
			absoluteDay = getAbsoluteDay(year, month, day);
		}
		return absoluteDay;
	}

	private int getAbsoluteMonthDay() {
		return ((year * 12) + month) * 100 + day;
	}

	public static SmallDate parseSmallDate(int isoDate) {
		return new SmallDate(isoDate / 10000, (isoDate / 100) % 100, isoDate % 100);
	}

	public static SmallDate parseSmallDate(String dateStr) {
		return parseSmallDate(dateStr, "yyyy-mm-dd", 0);
	}

	private static char findSeperator(String format) {
		char seperator = '\0';
		for (int i = 1; i < format.length(); i++) {
			if (!(format.charAt(i) == yearFormat || format.charAt(i) == monthFormat || format.charAt(i) == dayFormat)) {
				if (seperator == '\0' || seperator == format.charAt(i)) {
					seperator = format.charAt(i);
				} else {
					throw new IllegalArgumentException("More than one seperator found.");
				}
			}
		}
		return seperator;
	}

	private static boolean checkFormParts(String[] formParts) {
		if (formParts.length != datePartCount) {
			return false;
		}
		for (int i = 0; i < formParts.length; i++) {
			if (formParts[i].length() == 0) {
				throw new IllegalArgumentException("Double seperator.");
			} else {
				for (int j = 1; j < formParts[i].length(); j++) {
					if (formParts[i].charAt(j) != formParts[i].charAt(j - 1)) {
						throw new IllegalArgumentException("Mixed date parts.");
					}
				}
			}
		}
		return true;
	}

	private static boolean checkDateParts(String[] dateParts) {
		for (int i = 0; i < dateParts.length; i++) {
			if (dateParts[i].length() == 0) {
				return false;
			} else {
				for (int j = 0; j < dateParts[i].length(); j++) {
					if (!Character.isDigit(dateParts[i].charAt(j))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private static int getDatePartPosition(char formatSpecifier) {
		return formatSpecifier == yearFormat ? 0 : formatSpecifier == monthFormat ? 1 : 2;
	}

	private static int[] splitDigitString(String dateStr, String formatStr) throws IllegalArgumentException {
		if (dateStr.length() != formatStr.length()) {
			throw new IllegalArgumentException("Length of date to parse and format have to be the same.");
		}
		int start = 0;
		int[] ymd = new int[datePartCount];
		for (int i = 1; i < formatStr.length(); i++) {
			if (formatStr.charAt(i) != formatStr.charAt(i - 1)) {
				ymd[getDatePartPosition(formatStr.charAt(i - 1))] = Integer.parseInt(dateStr.substring(start, i));
				start = i;
			}
		}
		ymd[getDatePartPosition(formatStr.charAt(start))] = Integer.parseInt(dateStr.substring(start, formatStr.length()));
		return ymd;
	}

	private static int[] splitSeperatedString(String dateStr, String formatStr, char seperator) {
		String[] formParts = formatStr.split(Character.toString(seperator));
		String[] dateParts = dateStr.split(Character.toString(seperator));
		if (!checkFormParts(formParts) || !checkDateParts(dateParts)) {
			throw new IllegalArgumentException();
		}
		int[] ymd = new int[datePartCount];
		for (int i = 0; i < ymd.length; i++) {
			ymd[getDatePartPosition(formParts[i].charAt(0))] = Integer.parseInt(dateParts[i]);
		}
		return ymd;
	}

	public static SmallDate parseSmallDate(String dateStr, String formatStr) throws IllegalArgumentException {
		return parseSmallDate(dateStr, formatStr, 0);
	}

	public static SmallDate parseSmallDate(String dateStr, String formatStr, int windowMidpoint) throws IllegalArgumentException {
		char seperator = findSeperator(formatStr);
		int[] ymd;
		if (seperator == '\0') {
			ymd = splitDigitString(dateStr, formatStr);
		} else {
			ymd = splitSeperatedString(dateStr, formatStr, seperator);
		}
		if (windowMidpoint > 0) {
			if (ymd[yearPosition] < (windowMidpoint + 50) % 100) {
				ymd[yearPosition] += windowMidpoint;
			} else {
				ymd[yearPosition] += (windowMidpoint - 100);
			}
		}
		return new SmallDate(ymd[yearPosition], ymd[monthPosition], ymd[dayPosition]);
	}

//	public static SmallDate parseString(String dateStr, String formatStr) throws IllegalArgumentException {
//		if (formatStr.length() != dateStr.length())
//			throw new IllegalArgumentException("format exception");
//		int y = 0, m = 0, d = 0;
//		for (int i = 0; i < formatStr.length(); i++) {
//			switch (Character.toLowerCase(formatStr.charAt(i))) {
//			case 'y':
//				y = y * 10 + (dateStr.charAt(i) - '0');
//				break;
//			case 'm':
//				m = m * 10 + (dateStr.charAt(i) - '0');
//				break;
//			case 'd':
//				d = d * 10 + (dateStr.charAt(i) - '0');
//				break;
//			default:
//				if (dateStr.charAt(i) != formatStr.charAt(i))
//					throw new IllegalArgumentException("format exception");
//			}
//		}
//		if (y < 50)
//			y += 2000;
//		if (y < 100)
//			y += 1900;
//		return new SmallDate(y, m, d);
//	}

	public int toIsoDate() {
		return year * 10000 + month * 100 + day;
	}

	public String toIsoString() {
		return Integer.toString(toIsoDate());
	}

	public String toString() {
		return String.format("%04d-%02d-%02d", year, month, day);
	}

	public boolean equals(Object object) {
		if (object.getClass() == this.getClass() && ((SmallDate) object).toIsoDate() == toIsoDate()) {
			return true;
		}
		return false;
	}

	public int compareTo(SmallDate rhs) {
		return toIsoDate() - rhs.toIsoDate();
	}

	public Date toDate() {
		Calendar cal = (Calendar) calendar.clone();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);
		return cal.getTime();
	}

	public static int dateDiff(char unit, SmallDate d1, SmallDate d2) throws IllegalArgumentException {
		if (d1.compareTo(d2) == 0) {
			return 0;
		}
		switch (Character.toLowerCase(unit)) {
		case 'd':
			return d2.getDayNumber() - d1.getDayNumber();
		case 'w':
			return (d2.getDayNumber() - d1.getDayNumber()) / 7;
		case 'm':
			return monthDiff(d1, d2);
		case 'y':
			return yearDiff(d1, d2);
		default:
			throw new IllegalArgumentException("No such unit");
		}
	}

	public static int dateDiff(String unit, SmallDate d1, SmallDate d2) throws IllegalArgumentException {
		if (unit.length() != 1) {
			throw new IllegalArgumentException("No such unit");
		}
		return dateDiff(unit.charAt(0), d1, d2);
	}

	public int getDayOfWeek() {
		// January 1, 0001 = Monday = 2, Sunday = 1, Saturday = 7
		return ((getDayNumber()) % 7) + 1;
	}

	private static int yearDiff(SmallDate d1, SmallDate d2) {
		return (d2.toIsoDate() - d1.toIsoDate()) / 10000;
	}

	private static int monthDiff(SmallDate d1, SmallDate d2) {
		if (d1.compareTo(d2) > 1) {
			return -monthDiff(d2, d1);
		}
		return (d2.getAbsoluteMonthDay() - d1.getAbsoluteMonthDay()) / 100 + (d1.getDay() > d2.getDay() && d2.isLastDayOfMonth() ? 1 : 0);
	}

	private static boolean isValid(int y, int m, int d) {
		return (y >= 1 && y <= 9999 && m >= 1 && m <= 12 && d >= 1 && d <= getLastDayOfMonth(y, m));
	}

	public static SmallDate getEaster(int year) {
		int alpha = year % 19;
		int beta = year / 100;
		int gamma = year % 100;
		int delta = beta / 4;
		int epsilon = beta % 4;
		int zeta = (8 * beta + 13) / 25;
		int theta = ((11 * (beta - delta - zeta)) - 4) / 30;
		int phi = (7 * alpha + theta + 6) / 11;
		int psi = (19 * alpha + (beta - delta - zeta) + 15 - phi) % 29;
		int i = gamma / 4;
		int k = gamma % 4;
		int lambda = ((32 + 2 * epsilon) + 2 * i - k - psi) % 7;
		int n = (90 + psi + lambda) / 25;
		int p = (19 + psi + lambda + n) % 32;
		return new SmallDate(year, n, p);
	}

	public static int workDays(SmallDate d1, SmallDate d2) {
		if (d1.compareTo(d2) > 0) {
			return workDays(d2, d1);
		}
		int workDays = ((dateDiff('d', d1, d2) / 7) * 5);
		SmallDate weekStart = d1.addDays((workDays / 5) * 7);
		while (weekStart.compareTo(d2) < 1) { // less equal -> inclusive
			if (isWorkDay(weekStart)) {
				workDays++;
			}
			weekStart = weekStart.addDays(1);
		}
		return workDays;
	}

	public static boolean isWorkDay(SmallDate date) {
		return ((date.getDayOfWeek() - 1) % 6 > 0);
	}

	public static int getYearsWorkDays(int year) {
		int wDays = workDays(new SmallDate(year, 1, 2), new SmallDate(year, 12, 31));
		// Queens day (Dutch national holiday)
		if (isWorkDay(new SmallDate(year, 4, 30))) { 
			wDays--;
		}
		if (isWorkDay(new SmallDate(year, 12, 25))) { // Christmas
			wDays--;
		}
		if (isWorkDay(new SmallDate(year, 12, 26))) { // Boxing day
			wDays--;
		}
		// Easter Monday, Ascension day (Thursday), Pentecost Monday
		wDays -= 3;
		return wDays;
	}

	public static void main(String[] args) {
		System.out.println(new SmallDate(1965, 1, 14).addDays(15000));
		System.out.println(new SmallDate(2, 3, 1).toString());
		System.out.println(getYearsWorkDays(2011));
		StopWatch sw = new StopWatch();
		SmallDate d1 = new SmallDate(2010, 3, 31);
		SmallDate d2 = new SmallDate(2011, 4, 30);
		sw.start();
		for (int i = 0; i < 10000; i++){
			SmallDate.dateDiff('m', d1, d2);
		}
		sw.stop();
		System.out.println(sw.getTimeInMillis());
		d1 = new SmallDate(1600, 1, 31);
		d2 = new SmallDate(1600, 11, 30);
		sw.start();
		for (int i = 0; i < 10000; i++){
			SmallDate.dateDiff('m', d1, d2);
		}
		sw.stop();
		System.out.println(sw.getTimeInMillis());
		System.out.println(d1.compareTo(d2));
		System.out.println(d2.compareTo(d1));
		System.out.println(workDays(d1, d2));
		System.out.println(d1.getDayOfWeek());
//		System.out.println(d1.toDate().getDate() + 1);
//		if (d1.getDayOfWeek() != d1.toDate().getDay() + 1) {
//			System.out.println("test");
//		}

		d1 = new SmallDate(1600, 1, 1);
		System.out.println(d1.getDayOfWeek());
		System.out.println(d1.toDate());

		System.out.println("greg");
		System.out.println(d2.getDayOfWeek());
		// d2 = new SmallDate(2011, 1, 9);
		System.out.println(workDays(d1, d2));
		for (int i = 2011; i < 2012; i++) {
			System.out.println(i + " " + SmallDate.getYearsWorkDays(i));
		}
		System.out.println(d1.compareTo(d2));
		System.out.println(d2.compareTo(d1));
		// System.out.println();
		// System.out.println(new SmallDate(2009, 12, 31).getDayOfWeek());
		// System.out.println(new SmallDate(2010, 1, 1).getDayOfWeek());
		// System.out.println();
		// System.out.println(new SmallDate(2010, 12, 31).getDayOfWeek());
		// System.out.println(new SmallDate(2011, 1, 1).getDayOfWeek());
		// System.out.println(today().addDays(-1).getDayOfWeek());

		// System.out.println(new SmallDate(1, 1, 1).getDayOfWeek());
		// sw.start();
		// int dow = 1;
		// int right = 0;
		// int wrong = 0;
		//
		// for (int i = 1; i < 2010 * 366; i++) {
		// d1 = new SmallDate(i);
		// // System.out.println("error " + d1.toIsoDate() + " " +
		// // d1.getDayOfWeek());
		// if (dateDiff('d', d1, new SmallDate(i + 1)) != 1) {
		// System.out.println("error");
		// }
		// if (d1.getDayOfWeek() != d1.toDate().getDay() + 1) {
		// System.out.println("test");
		// }
		// if (d1.getDayOfWeek() != dow) {
		// wrong++;
		// //System.out.println("error " + d1.toIsoDate() + " " +
		// d1.getDayOfWeek());
		// //return;
		// } else {
		// right++;
		// }
		// if (dow == 7) {
		// dow = 1;
		// } else {
		// dow++;
		// }
		// }
		// sw.stop();
		// System.out.println(right);
		// System.out.println(wrong);
		// System.out.println(sw.getTimeInMillis());
		// d1 = new SmallDate(1);
		// String tt = "";
		//
		// sw.start();
		// for (int i = 1990; i < 2020; i++) {
		// System.out.println(i + " " +isLeapYear(i));
		// }
		// for (int i = 1890; i < 1910; i++) {
		// System.out.println(i + " " +isLeapYear(i));
		// }
		// sw.stop();
		// System.out.println(SmallDate.getEaster(2009));
		// System.out.println(sw.getTimeInMillis());
		// sw.start();
		// for (int i = 0; i < 90000; i++) {
		// SmallDate.isLeapYear(i / 10);
		// }
		// sw.stop();
		// System.out.println(SmallDate.getEaster(2009));
		// System.out.println(sw.getTimeInMillis());
		// sw.start();
		// for (int i = 0; i < 90000; i++) {
		// SmallDate.isLeapYearOld(i / 10);
		// }
		// sw.stop();
		// System.out.println(SmallDate.getEaster(2009));
		// System.out.println(sw.getTimeInMillis());
		// sw.start();
		// for (int i = 0; i < 90000; i++) {
		// SmallDate.isLeapYearOld(i / 10);
		// }
		// sw.stop();
		// System.out.println(SmallDate.getEaster(2009));
		// System.out.println(sw.getTimeInMillis());
		// System.out.println(SmallDate.isLeapYear(1900));
		// System.out.println(SmallDate.isLeapYear(2000));
		// System.out.println(SmallDate.isLeapYear(2004));
		// System.out.println(SmallDate.isLeapYear(2100));
		// for (int i = 2010; i < 2015; i++) {
		// System.out.println(i + " " + SmallDate.getEaster(i));
		// }
		// System.out.println(tt);
		// System.out.println(SmallDate.dateDiff('d', new SmallDate(2011, 10,
		// 3), new SmallDate(2013, 12, 2)));
		// System.out.println(SmallDate.dateDiff('d', new SmallDate(2013, 12,
		// 2), new SmallDate(2011, 10, 3)));
		//
		// System.out.println("week days");
		// SmallDate d1 = new SmallDate(2011, 3, 21);
		// //d1 = new SmallDate(1, 1, 1);
		//
		// SmallDate d2 = new SmallDate(2011, 3, 28);
		// System.out.println(d1.getDayOfWeek());
		// System.out.println(d2.getDayOfWeek());
		// int days = SmallDate.dateDiff('d', d1, d2);
		// System.out.println("days");
		// System.out.println(days);
		// int wd = (days / 7) * 5;
		// System.out.println(wd);
		// // wd += (d2.getDayOfWeek() - d1.getDayOfWeek());
		// //System.out.println(d2.getDayOfWeek() - d1.getDayOfWeek());
		// wd += Math.max(6 - d1.getDayOfWeek(), 0);
		// // System.out.println(wd);
		// wd += Math.min(d2.getDayOfWeek(), 5);
		//
		// System.out.println(wd);

		// System.out.println(SmallDate.monthDiff2(new SmallDate(2013, 12, 2),
		// new SmallDate(2013, 12, 2)));
		// System.out.println(SmallDate.monthDiff2(new SmallDate(2011, 10, 3),
		// new SmallDate(2013, 12, 2)));
		// System.out.println(SmallDate.monthDiff2(new SmallDate(2013, 12, 2),
		// new SmallDate(2011, 10, 3)));
		//
		// System.out.println(SmallDate.monthDiff3(new SmallDate(2013, 12, 2),
		// new SmallDate(2013, 12, 2)));
		// System.out.println(SmallDate.monthDiff3(new SmallDate(2011, 10, 3),
		// new SmallDate(2013, 12, 2)));
		// System.out.println(SmallDate.monthDiff3(new SmallDate(2013, 12, 2),
		// new SmallDate(2011, 10, 3)));
		//
		// System.out.println(SmallDate.yearDiff2(new SmallDate(2013, 12, 2),
		// new SmallDate(2013, 12, 2)));
		// System.out.println(SmallDate.yearDiff2(new SmallDate(2011, 10, 3),
		// new SmallDate(2013, 12, 2)));
		// System.out.println(SmallDate.yearDiff2(new SmallDate(2013, 12, 2),
		// new SmallDate(2011, 10, 3)));

	}
}
