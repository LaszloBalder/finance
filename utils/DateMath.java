package nl.yasmijn.utils;

import java.util.Calendar;

public abstract class DateMath {
	private static int[] monthEnds = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static int[] monthCumms = { 0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };

	public static int getAge(int dateOfBirth, int calcDate) {
		return (calcDate - dateOfBirth) / 10000;
	}

	public static int getAge(QuickDate dateOfBirth, QuickDate calcDate) {
		return (calcDate.getDate() - dateOfBirth.getDate()) / 10000;
	}

	public static int getToday() {
		Calendar calendar = Calendar.getInstance();
		int today = calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100
				+ calendar.get(Calendar.DAY_OF_MONTH);
		return today;
	}

	public static int getMonthDiff(int date1, int date2) {
		if (!(isValidDate(date1) && isValidDate(date2))) {
			throw new IllegalArgumentException();
		}
		if ((date1 / 100) == (date2 / 100)) {
			return 0;
		}
		if (date1 > date2) {
			return -getMonthDiff(date2, date1);
		}
		int monthDiff = ((date2 / 100) % 100) - ((date1 / 100) % 100);
		if ((date2 % 100) < (date1 % 100))
			monthDiff--;
		if (monthDiff < 0) {
			monthDiff += 12;
		}
		return ((date2 - date1) / 10000 * 12) + monthDiff;
	}

	public static int getAge(int dateOfBirth) {
		return (getToday() - dateOfBirth) / 10000;
	}

	public static int getAge(QuickDate dateOfBirth) {
		return (getToday() - dateOfBirth.getDate()) / 10000;
	}

	public static int getAbsoluteDate(int date) {
		if (!isValidDate(date)) {
			throw new IllegalArgumentException();
		}
		int year = date / 10000;
		int absDate = (year * 365) + (year / 4) - (year / 100) + (year / 400) + (date % 100);
		absDate += monthCumms[(date / 100) % 100];
		if (isLeapYear(year) && ((date / 100) % 100) > 2)
			absDate++;
		return absDate;
	}
	
	

	public static boolean isValidDate(int date) {
		if (date < 10000101 || date > 99991231) {
			return false;
		}
		int month = (date / 100) % 100;
		if (month < 1 || month > 12) {
			return false;
		}
		int day = date % 100;
		if (day < 1 || day > monthEnds[month - 1]) {
			if (month == 2 && day == 29 && isLeapYear(date / 10000)) {
				return true;
			}
			return false;
		}
		return true;
	}

	public static boolean isLeapYear(int year) {
		return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
	}

	public static double getYearFraction(SmallDate start, SmallDate end, DayCalcMethod method) {
		double fraction = 0;
		SmallDate interestEnd = end.addDays(-1);
		switch (method) {
		case Actual360: {

			// for (int i = ; i )
			return 0;
		}
		case ActualActual: {
			if (start.getYear() == interestEnd.getYear()) {
				fraction = 1.0 * SmallDate.dateDiff('D', start, interestEnd) / getDaysInYear(start.getYear());
			} else {
				fraction = 1.0 * SmallDate.dateDiff('D', start, new SmallDate(start.getYear() + 1, 1, 1))
						/ getDaysInYear(start.getYear());
				fraction += 1.0
						* SmallDate.dateDiff('D', new SmallDate(interestEnd.getYear() - 1, 12, 31), interestEnd)
						/ getDaysInYear(interestEnd.getYear());
				fraction += interestEnd.getYear() - start.getYear() + 1;
			}
		}
		case Thirty360: {
			fraction = getDays360(start, end) / 360.0;
		}
		case ThirtyActual: {
			return 0;
		}

		}
		return fraction;
	}

	public static int getDaysInYear(int year) {
		return isLeapYear(year) ? 366 : 365;
	}

	public static int getDays360(SmallDate start, SmallDate end) {
		int days360 = 0;
		days360 = 360 * (end.getYear() - start.getYear()) + 30 * (end.getMonth() - start.getMonth())
				+ ((end.isLastDayOfMonth() ? 30 : end.getDay()) - (start.isLastDayOfMonth() ? 30 : start.getDay()));

		return days360;
	}

	public static void main(String[] args) {
		// for (int i = 2090; i < 2105; i++) {
		// System.out.println(i);
		// System.out.println(DateMath.isLeapYear(i));
		// System.out.println(DateMath.isValidDate(i*10000+228));
		// System.out.println(DateMath.isValidDate(i*10000+229));
		// System.out.println(DateMath.isValidDate(i*10000+230));
		// }
		System.out.println(DateMath.getAge(19650114, 20110112));
		System.out.println(DateMath.getAge(19650114, 20110113));
		System.out.println(DateMath.getAge(19650114, 20110114));
		System.out.println(DateMath.getAge(19650114, 20110115));
		System.out.println(DateMath.getToday());
		System.out.println(DateMath.isValidDate(18000101));
		System.out.println(DateMath.isValidDate(21000229));
		System.out.println(DateMath.getMonthDiff(20010202, 20030101));
		System.out.println(DateMath.getMonthDiff(20010228, 20010301));
		System.out.println(DateMath.getMonthDiff(20010302, 20010301));
		System.out.println(DateMath.getAbsoluteDate(10000101));
		System.out.println(DateMath.getAbsoluteDate(10010101));
		System.out.println(DateMath.getAbsoluteDate(10020101));
		System.out.println(DateMath.getAbsoluteDate(10030101));
		System.out.println(DateMath.getAbsoluteDate(19650114));
		System.out.println(DateMath.getAbsoluteDate(20110311));
		SmallDate d1 = new SmallDate(2011, 2, 2);
		SmallDate d2 = new SmallDate(2011, 3, 3);
		System.out.println(getDays360(d1, d2));
		d1 = new SmallDate(2011, 1, 1);
		d2 = new SmallDate(2012, 1, 1);
		System.out.println(getDays360(d1, d2));
		d1 = new SmallDate(2011, 1, 31);
		d2 = new SmallDate(2011, 2, 28);
		// System.out.println(getDays360(d1, d2));
		// d1 = new SmallDate(2011, 1, 30);
		// d2 = new SmallDate(2012, 1, 1);
		// System.out.println(getDays360(d1, d2));
		 d1 = new SmallDate(2011, 1, 31);
		 d2 = new SmallDate(2012, 1, 1);
		 System.out.println(getDays360(d1, d2));
		StopWatch sw = new StopWatch();
		int tt = 0;
		sw.start();
		for (int i = 0; i < 1000000; i++)
			tt += getDays360(d1, d2);
		sw.stop();
		System.out.println(sw.getTimeInMillis());

	}
}
