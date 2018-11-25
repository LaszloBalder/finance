package nl.yasmijn.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class SmallDateTest {

	@Test
	public void testSmallDateIntIntInt() {
		SmallDate d = new SmallDate(1, 1, 1);
		assertTrue(d.getYear() == 1 && d.getMonth() == 1 && d.getDay() == 1);
		d = new SmallDate(2011, 3, 28);
		assertTrue(d.getYear() == 2011 && d.getMonth() == 3 && d.getDay() == 28);
		assertTrue(new SmallDate(2000, 1, 31).toIsoDate() == 20000131);
		assertTrue(new SmallDate(2000, 2, 29).toIsoDate() == 20000229);
		assertTrue(new SmallDate(2001, 2, 28).toIsoDate() == 20010228);
		//assertTrue(new SmallDate(2001,2,29).toIsoDate() == 20010229);
		assertTrue(new SmallDate(2000, 3, 31).toIsoDate() == 20000331);
		assertTrue(new SmallDate(2000, 4, 30).toIsoDate() == 20000430);
		assertTrue(new SmallDate(2000, 5, 31).toIsoDate() == 20000531);
		assertTrue(new SmallDate(2000, 6, 30).toIsoDate() == 20000630);
		assertTrue(new SmallDate(2000, 7, 31).toIsoDate() == 20000731);
		assertTrue(new SmallDate(2000, 8, 31).toIsoDate() == 20000831);
		assertTrue(new SmallDate(2000, 9, 30).toIsoDate() == 20000930);
		assertTrue(new SmallDate(2000, 10, 31).toIsoDate() == 20001031);
		assertTrue(new SmallDate(2000, 11, 30).toIsoDate() == 20001130);
		assertTrue(new SmallDate(2000, 12, 31).toIsoDate() == 20001231);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010132() {
		new SmallDate(2001, 1, 32);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010229() {
		new SmallDate(2001, 2, 29);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20000230() {
		new SmallDate(2000, 2, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010332() {
		new SmallDate(2001, 3, 32);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010431() {
		new SmallDate(2001, 4, 31);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010532() {
		new SmallDate(2001, 5, 32);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010631() {
		new SmallDate(2001, 6, 31);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010732() {
		new SmallDate(2001, 7, 32);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010832() {
		new SmallDate(2001, 8, 32);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010931() {
		new SmallDate(2001, 9, 31);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20011032() {
		new SmallDate(2001, 10, 32);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20011631() {
		new SmallDate(2001, 11, 31);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20011232() {
		new SmallDate(2001, 12, 32);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate00010101() {
		new SmallDate(0, 01, 01);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20010020() {
		new SmallDate(2001, 0, 20);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate20011320() {
		new SmallDate(2001, 13, 20);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate100001220() {
		new SmallDate(10000, 12, 20);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSeperator() {
		SmallDate.parseSmallDate("2006-01-01", "yyyy-mm/dd");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMixedParts() {
		SmallDate.parseSmallDate("2006-01-01", "yyyy-dm-md");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongSeperator() {
		SmallDate.parseSmallDate("2006-01-01", "yyyy-mmdd-");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongLength() {
		SmallDate.parseSmallDate("20060101", "yyyymdd");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDate() {
		SmallDate.parseSmallDate("2006-x1-01", "yyyy-mm-dd");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDiffUnitWrong() {
		SmallDate.dateDiff('x', new SmallDate(2006, 1, 1), new SmallDate(2006, 1, 5));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDiffUnitToLong() {
		SmallDate.dateDiff("aa", new SmallDate(2006, 1, 1), new SmallDate(2006, 1, 5));
	}

	@Test
	public void testSmallDateInt() {
		SmallDate d = new SmallDate(1);
		assertTrue(d.getYear() == 1 && d.getMonth() == 1 && d.getDay() == 1);
		d = new SmallDate(1 + 365);
		assertTrue(d.getYear() == 2 && d.getMonth() == 1 && d.getDay() == 1);
		d = new SmallDate(1 + 365 + 365);
		assertTrue(d.getYear() == 3 && d.getMonth() == 1 && d.getDay() == 1);
		d = new SmallDate(1 + 365 + 365 + 365);
		assertTrue(d.getYear() == 4 && d.getMonth() == 1 && d.getDay() == 1);
		d = new SmallDate(1 + 365 + 365 + 365 + 365);
		assertTrue(d.getYear() == 4 && d.getMonth() == 12 && d.getDay() == 31);
		d = new SmallDate(1 + 365 + 365 + 365 + 366);
		assertTrue(d.getYear() == 5 && d.getMonth() == 1 && d.getDay() == 1);
	}

	@Test
	public void testToIsoDate() {
		SmallDate d = new SmallDate(1, 1, 1);
		assertTrue(d.toIsoDate() == 10101);
		d = new SmallDate(2011, 3, 28);
		assertTrue(d.toIsoDate() == 20110328);
		assertTrue(new SmallDate(2012, 02, 29).toIsoDate() == 20120229);
		assertTrue(new SmallDate(2012, 03, 31).toIsoDate() == 20120331);
		assertTrue(new SmallDate(1965, 01, 14).toIsoDate() == 19650114);
	}

	@Test
	public void testSmallDateDate() {
		Date d = new Date();
		SmallDate t = new SmallDate(d);
		Calendar c = Calendar.getInstance();
		assertTrue(t.getDay() == c.get(Calendar.DAY_OF_MONTH));
		assertTrue(t.getMonth() == c.get(Calendar.MONTH) + 1);
		assertTrue(t.getYear() == c.get(Calendar.YEAR));
	}

	@Test
	public void testToday() {
		SmallDate t = SmallDate.today();
		Calendar c = Calendar.getInstance();
		assertTrue(t.getDay() == c.get(Calendar.DAY_OF_MONTH));
		assertTrue(t.getMonth() == c.get(Calendar.MONTH) + 1);
		assertTrue(t.getYear() == c.get(Calendar.YEAR));
	}

	@Test
	public void testAddDays() {
		assertTrue(new SmallDate(2012, 2, 1).addDays(29).toIsoDate() == 20120301);
		assertTrue(new SmallDate(2012, 2, 1).addDays(28).toIsoDate() == 20120229);
		assertTrue(new SmallDate(2012, 2, 1).addDays(2).toIsoDate() == 20120203);
		assertTrue(new SmallDate(2011, 1, 1).addDays(365).toIsoDate() == 20120101);
		assertTrue(new SmallDate(2012, 1, 1).addDays(366).toIsoDate() == 20130101);
	}

	@Test
	public void testAddMonths() {
		assertTrue(new SmallDate(2012, 1, 31).addMonths(1).toIsoDate() == 20120229);
		assertTrue(new SmallDate(2013, 1, 31).addMonths(1).toIsoDate() == 20130228);
		assertTrue(new SmallDate(2012, 1, 31).addMonths(12).toIsoDate() == 20130131);
		assertTrue(new SmallDate(2012, 1, 31).addMonths(2).toIsoDate() == 20120331);
		assertTrue(new SmallDate(2012, 1, 31).addMonths(3).toIsoDate() == 20120430);
		assertTrue(new SmallDate(2012, 1, 1).addMonths(3).toIsoDate() == 20120401);
		assertTrue(new SmallDate(2012, 1, 15).addMonths(3).toIsoDate() == 20120415);
		assertTrue(new SmallDate(2012, 1, 31).addMonths(5).toIsoDate() == 20120630);
		assertTrue(new SmallDate(2012, 1, 31).addMonths(120).toIsoDate() == 20220131);
	}

	@Test
	public void testAddYears() {
		assertTrue(new SmallDate(2012, 1, 1).addYears(1).toIsoDate() == 20130101);
		assertTrue(new SmallDate(2012, 2, 29).addYears(1).toIsoDate() == 20130228);
		assertTrue(new SmallDate(2013, 2, 28).addYears(1).toIsoDate() == 20140228);
	}

	@Test
	public void testGetDayNumber() {
		assertTrue(new SmallDate(1, 1, 1).getDayNumber() == 1);
		assertTrue(new SmallDate(5, 1, 1).getDayNumber() == (1 + 365 + 365 + 365 + 366));
	}

	@Test
	public void testIsLeapYear() {
		assertTrue(SmallDate.isLeapYear(4));
		assertTrue(SmallDate.isLeapYear(8));
		assertTrue(SmallDate.isLeapYear(1996));
		assertTrue(SmallDate.isLeapYear(2000));
		assertTrue(SmallDate.isLeapYear(2004));
		assertTrue(SmallDate.isLeapYear(2008));
		assertTrue(!SmallDate.isLeapYear(1));
		assertTrue(!SmallDate.isLeapYear(2));
		assertTrue(!SmallDate.isLeapYear(3));
		assertTrue(!SmallDate.isLeapYear(5));
		assertTrue(!SmallDate.isLeapYear(1500));
		assertTrue(!SmallDate.isLeapYear(1900));
		assertTrue(!SmallDate.isLeapYear(2001));
		assertTrue(!SmallDate.isLeapYear(2002));
		assertTrue(!SmallDate.isLeapYear(2003));
		assertTrue(!SmallDate.isLeapYear(2005));
	}

	@Test
	public void testParseIsoDate() {
		assertTrue(SmallDate.parseSmallDate(20110101).toIsoDate() == 20110101);
		assertTrue(SmallDate.parseSmallDate(20110128).toIsoDate() == 20110128);
		assertTrue(SmallDate.parseSmallDate(20110228).toIsoDate() == 20110228);
		assertTrue(SmallDate.parseSmallDate(20110331).toIsoDate() == 20110331);
		assertTrue(SmallDate.parseSmallDate(19650114).toIsoDate() == 19650114);
	}

	@Test
	public void testParseString() {
		assertTrue(SmallDate.parseSmallDate("2011-02-28", "yyyy-mm-dd").toIsoDate() == 20110228);
		assertTrue(SmallDate.parseSmallDate("2011-02-28", "y-m-d").toIsoDate() == 20110228);
		assertTrue(SmallDate.parseSmallDate("28/02/2011", "dd/mm/yyyy").toIsoDate() == 20110228);
		assertTrue(SmallDate.parseSmallDate("28/02/2011", "d/m/y").toIsoDate() == 20110228);
		assertTrue(SmallDate.parseSmallDate("28u02u2011", "dumuy").toIsoDate() == 20110228);
		assertTrue(SmallDate.parseSmallDate("28-02-2011", "dd-mm-yyyy").toIsoDate() == 20110228);
		assertTrue(SmallDate.parseSmallDate("02/28/2011", "mm/dd/yyyy").toIsoDate() == 20110228);
		assertTrue(SmallDate.parseSmallDate("02/28/11", "mm/dd/yy", 2000).toIsoDate() == 20110228);
		assertTrue(SmallDate.parseSmallDate("02/28/65", "mm/dd/yy", 2000).toIsoDate() == 19650228);
		assertTrue(SmallDate.parseSmallDate("022865", "mmddyy", 2000).toIsoDate() == 19650228);
	}

	@Test
	public void testToString() {
		SmallDate d1 = new SmallDate(2011, 3, 21); // Monday
		SmallDate d2 = new SmallDate(2, 3, 1); // Monday
		assertTrue(d1.toString().equals("2011-03-21"));
		assertTrue(d2.toString().equals("0002-03-01"));
	}

	@Test
	public void testEqualsObject() {
		assertFalse(new SmallDate(2011, 1, 1).equals(new SmallDate(2011, 1, 2)));
		assertFalse(new SmallDate(2011, 1, 3).equals(new SmallDate(2011, 1, 2)));
		assertTrue(new SmallDate(2011, 1, 1).equals(new SmallDate(2011, 1, 1)));
		SmallDate d1 = new SmallDate(2011, 3, 21); // Monday
		SmallDate d2 = new SmallDate(2011, 3, 28); // Monday
		assertFalse(d1.equals(d2) != d2.equals(d1));
		d2 = new SmallDate(d1.getDayNumber());
		assertTrue(d1.equals(d2));
		assertTrue(d2.equals(d1));
		assertTrue(d2.equals(d2.addDays(0)));
		assertTrue(d2.equals(d2.addMonths(0)));
		assertTrue(d2.equals(d2.addYears(0)));
	}

	@Test
	public void testCompareTo() {
		assertTrue(new SmallDate(2011, 1, 1).compareTo(new SmallDate(2012, 1, 1)) < 0);
		assertTrue(new SmallDate(2012, 1, 1).compareTo(new SmallDate(2011, 1, 1)) > 0);
		assertTrue(new SmallDate(2012, 1, 1).compareTo(new SmallDate(2012, 1, 1)) == 0);
	}

	@Test
	public void testToDate() {
		SmallDate t = new SmallDate(2011, 3, 28);
		Date d = t.toDate();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		assertTrue(t.getDay() == c.get(Calendar.DAY_OF_MONTH));
		assertTrue(t.getMonth() == c.get(Calendar.MONTH) + 1);
		assertTrue(t.getYear() == c.get(Calendar.YEAR));
	}

	@Test
	public void testDateDiffCharSmallDateSmallDate() {
		assertTrue(SmallDate.dateDiff('d', new SmallDate(2011, 1, 1), new SmallDate(2012, 1, 1)) == 365);
		assertTrue(SmallDate.dateDiff('d', new SmallDate(1965, 1, 14), new SmallDate(2006, 2, 8)) == 15000);
		assertTrue(SmallDate.dateDiff('m', new SmallDate(2011, 1, 1), new SmallDate(2012, 1, 1)) == 12);
		assertTrue(SmallDate.dateDiff('y', new SmallDate(2011, 1, 1), new SmallDate(2012, 1, 1)) == 1);
		assertTrue(SmallDate.dateDiff('m', new SmallDate(2011, 1, 1), new SmallDate(2011, 12, 31)) == 11);
		assertTrue(SmallDate.dateDiff('d', new SmallDate(2011, 1, 1), new SmallDate(2011, 12, 31)) == 364);
		assertTrue(SmallDate.dateDiff('y', new SmallDate(2011, 1, 1), new SmallDate(2011, 12, 31)) == 0);
		assertTrue(SmallDate.dateDiff('y', new SmallDate(2012, 2, 29), new SmallDate(2013, 2, 28)) == 0);
		assertTrue(SmallDate.dateDiff('m', new SmallDate(2011, 5, 31), new SmallDate(2011, 6, 30)) == 1);
		assertTrue(SmallDate.dateDiff('m', new SmallDate(2011, 6, 30), new SmallDate(2011, 5, 31)) == -1);
	}

	@Test
	public void testDateDiffStringSmallDateSmallDate() {
		assertTrue(SmallDate.dateDiff("d", new SmallDate(2011, 1, 1), new SmallDate(2012, 1, 1)) == 365);
		assertTrue(SmallDate.dateDiff("d", new SmallDate(1965, 1, 14), new SmallDate(2006, 2, 8)) == 15000);
		assertTrue(SmallDate.dateDiff("m", new SmallDate(2011, 1, 1), new SmallDate(2012, 1, 1)) == 12);
		assertTrue(SmallDate.dateDiff("y", new SmallDate(2011, 1, 1), new SmallDate(2012, 1, 1)) == 1);
		assertTrue(SmallDate.dateDiff("m", new SmallDate(2011, 1, 1), new SmallDate(2011, 12, 31)) == 11);
		assertTrue(SmallDate.dateDiff("d", new SmallDate(2011, 1, 1), new SmallDate(2011, 12, 31)) == 364);
		assertTrue(SmallDate.dateDiff("y", new SmallDate(2011, 1, 1), new SmallDate(2011, 12, 31)) == 0);
	}

	@Test
	public void testGetDayOfWeek() {
		assertTrue(new SmallDate(1, 1, 1).getDayOfWeek() == 2);
		assertTrue(new SmallDate(2, 1, 1).getDayOfWeek() == 3);
		assertTrue(new SmallDate(3, 1, 1).getDayOfWeek() == 4);
		assertTrue(new SmallDate(4, 1, 1).getDayOfWeek() == 5);
		assertTrue(new SmallDate(5, 1, 1).getDayOfWeek() == 7);
		assertTrue(new SmallDate(6, 1, 1).getDayOfWeek() == 1);
		assertTrue(new SmallDate(7, 1, 1).getDayOfWeek() == 2);
		assertTrue(new SmallDate(8, 1, 1).getDayOfWeek() == 3);
		assertTrue(new SmallDate(9, 1, 1).getDayOfWeek() == 5);
		assertTrue(new SmallDate(10, 1, 1).getDayOfWeek() == 6);
		assertTrue(new SmallDate(11, 1, 1).getDayOfWeek() == 7);
		assertTrue(new SmallDate(12, 1, 1).getDayOfWeek() == 1);
		assertTrue(new SmallDate(13, 1, 1).getDayOfWeek() == 3);
		assertTrue(new SmallDate(1, 1, 8).getDayOfWeek() == 2);
		assertTrue(new SmallDate(2011, 1, 1).getDayOfWeek() == 7);

		assertTrue(new SmallDate(2011, 3, 27).getDayOfWeek() == 1);

		// 2012 starts on a Sunday
		assertTrue(new SmallDate(2012, 1, 1).getDayOfWeek() == 1);
		assertTrue(new SmallDate(2012, 1, 2).getDayOfWeek() == 2);
		assertTrue(new SmallDate(2012, 1, 3).getDayOfWeek() == 3);
		assertTrue(new SmallDate(2012, 1, 4).getDayOfWeek() == 4);
		assertTrue(new SmallDate(2012, 1, 5).getDayOfWeek() == 5);
		assertTrue(new SmallDate(2012, 1, 6).getDayOfWeek() == 6);
		assertTrue(new SmallDate(2012, 1, 7).getDayOfWeek() == 7);
		assertTrue(new SmallDate(2012, 1, 8).getDayOfWeek() == 1);
	}

	@Test
	public void testGetEaster() {
		SmallDate d = SmallDate.getEaster(2011);
		assertTrue(d.toIsoDate() == 20110424);
	}

	@Test
	public void testWorkDays() {
		SmallDate d1 = new SmallDate(2011, 3, 21); // Monday
		SmallDate d2 = new SmallDate(2011, 3, 28); // Monday
		assertTrue(SmallDate.workDays(d1, d2) == 6);
		d1 = new SmallDate(2011, 3, 19); // Saturday
		d2 = new SmallDate(2011, 3, 27); // Sunday
		assertTrue(SmallDate.workDays(d1, d2) == 5);
		d1 = new SmallDate(2011, 2, 1);
		d2 = new SmallDate(2011, 2, 28);
		assertTrue(SmallDate.workDays(d1, d2) == 20);
		d1 = new SmallDate(2011, 3, 1);
		d2 = new SmallDate(2011, 3, 31);
		assertTrue(SmallDate.workDays(d1, d2) == 23);
		d1 = new SmallDate(2010, 1, 1);
		d2 = new SmallDate(2010, 12, 31);
		assertTrue(SmallDate.workDays(d1, d2) == 261);
		d1 = new SmallDate(2011, 1, 1);
		d2 = new SmallDate(2011, 12, 31);
		assertTrue(SmallDate.workDays(d1, d2) == 260);
		d1 = new SmallDate(2012, 1, 1);
		d2 = new SmallDate(2012, 12, 31);
		assertTrue(SmallDate.workDays(d1, d2) == 261);
		d1 = new SmallDate(2008, 1, 1);
		d2 = new SmallDate(2008, 12, 31);
		assertTrue(SmallDate.workDays(d1, d2) == 262);
		d2 = new SmallDate(2010, 1, 1);
		d1 = new SmallDate(2010, 12, 31);
		assertTrue(SmallDate.workDays(d1, d2) == 261);
	}

	@Test
	public void testIsWorkDay() {
		SmallDate d1 = new SmallDate(2011, 3, 20); // sun
		assertFalse(SmallDate.isWorkDay(d1));
		d1 = new SmallDate(2011, 3, 21); // mon
		assertTrue(SmallDate.isWorkDay(d1));
		d1 = new SmallDate(2011, 3, 22); // tue
		assertTrue(SmallDate.isWorkDay(d1));
		d1 = new SmallDate(2011, 3, 23); // wed
		assertTrue(SmallDate.isWorkDay(d1));
		d1 = new SmallDate(2011, 3, 24); // thu
		assertTrue(SmallDate.isWorkDay(d1));
		d1 = new SmallDate(2011, 3, 25); // fri
		assertTrue(SmallDate.isWorkDay(d1));
		d1 = new SmallDate(2011, 3, 26); // sat
		assertFalse(SmallDate.isWorkDay(d1));
	}

	@Test
	public void testGetYearsWorkDays() {
		assertTrue(SmallDate.getYearsWorkDays(2006) == 255);
		assertTrue(SmallDate.getYearsWorkDays(2007) == 254);
		assertTrue(SmallDate.getYearsWorkDays(2008) == 255);
		assertTrue(SmallDate.getYearsWorkDays(2009) == 255);
		assertTrue(SmallDate.getYearsWorkDays(2010) == 256);
		assertTrue(SmallDate.getYearsWorkDays(2011) == 256);
		assertTrue(SmallDate.getYearsWorkDays(2012) == 255);
		assertTrue(SmallDate.getYearsWorkDays(2013) == 254);
		assertTrue(SmallDate.getYearsWorkDays(2014) == 254);
		assertTrue(SmallDate.getYearsWorkDays(2015) == 255);
		assertTrue(SmallDate.getYearsWorkDays(2016) == 256);
		assertTrue(SmallDate.getYearsWorkDays(2017) == 255);
		assertTrue(SmallDate.getYearsWorkDays(2018) == 254);
		assertTrue(SmallDate.getYearsWorkDays(2019) == 254);
		assertTrue(SmallDate.getYearsWorkDays(2020) == 256);
		assertTrue(SmallDate.getYearsWorkDays(2021) == 256);
		assertTrue(SmallDate.getYearsWorkDays(2022) == 256);
		assertTrue(SmallDate.getYearsWorkDays(2023) == 255);
	}
}
