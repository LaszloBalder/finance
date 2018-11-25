package test;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class PeriodicPayment {
	private int startPeriod;
	private int endPeriod;
	private double amount;
	private int duration;
	private PmtFreq pmtFreq;

	public PeriodicPayment(int startPeriod, int endPeriod, double amount, PmtFreq pmtFreq) {
		if (!isValidPeriod(startPeriod, pmtFreq) || !isValidPeriod(endPeriod, pmtFreq) || amount < 1 || startPeriod > endPeriod
				|| pmtFreq.getFraction() == 0) {
			throw new IllegalArgumentException();
		}
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
		this.amount = amount;
		this.pmtFreq = pmtFreq;
		this.duration = getPeriodsBetween(startPeriod, endPeriod, pmtFreq);
	}

	public PmtFreq getPmtFreq() {
		return pmtFreq;
	}

	public double getYearAmount(int year) {
		return getAmount(year * 100 + 1, year * 100 + pmtFreq.getFraction());
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public int getEndPeriod() {
		return endPeriod;
	}

	public int getStartPeriod() {
		return startPeriod;
	}

	public boolean isVariable() {
		return false;
	}

	public int getDuration() {
		return duration;
	}

	public double getMonthAmount(int period) {
		if (!isValidPeriod(period, pmtFreq)) {
			throw new IllegalArgumentException();
		}
		if (period >= startPeriod && period <= endPeriod) {
			return amount;
		} else {
			return 0.0;
		}
	}

	public double getAmount(int startQueryPeriod, int endQueryPeriod) {
		if (!isValidPeriod(startQueryPeriod, pmtFreq) || !isValidPeriod(endQueryPeriod, pmtFreq)) {
			throw new IllegalArgumentException();
		}
		if (startQueryPeriod > endPeriod || endQueryPeriod < startPeriod) {
			return 0;
		}
		int start = startQueryPeriod > startPeriod ? startQueryPeriod : startPeriod;
		int end = endQueryPeriod < endPeriod ? endQueryPeriod : endPeriod;
		return getPeriodsBetween(start, end, pmtFreq) * amount;
	}

	protected static boolean isValidPeriod(int yearPeriod, PmtFreq pmtFreq) {
		if (yearPeriod / 100 < 1900 || yearPeriod / 100 > 9999)
			return false;
		int period = yearPeriod % 100;
		return (period > 0 && period <= pmtFreq.getFraction());
	}

	public static int getPeriodsBetween(int start, int end, PmtFreq pmtFreq) {
		if (end < start) {
			return -getPeriodsBetween(end, start, pmtFreq);
		}
		int raw = end - start;
		int years = raw / 100;
		int months = raw % 100;
		if (months < pmtFreq.getFraction()) {
			return years * pmtFreq.getFraction() + months + 1;
		} else {
			return (years * pmtFreq.getFraction()) + (months - (100 - pmtFreq.getFraction())) + 1;
		}
	}

	public static int addPeriods(int period, int periods, PmtFreq pmtFreq) {
		int temp = period % 100 + periods - 1;
		return ((period / 100) * 100) + ((temp / pmtFreq.getFraction()) * 100) + ((temp % pmtFreq.getFraction()) + 1);
	}

	public static SortedMap<Integer, Change> getChangeList(List<? extends PeriodicPayment> pmtList) {
		SortedMap<Integer, Change> changeList = new TreeMap<Integer, Change>();
		for (PeriodicPayment pmt : pmtList) {
			if (changeList.containsKey(pmt.getStartPeriod())) {
				Change val = changeList.get(pmt.getStartPeriod());
				val.amount += pmt.getMonthAmount(pmt.getStartPeriod());
			} else {
				Change val = new Change();
				val.period = pmt.getStartPeriod();
				val.amount = pmt.getMonthAmount(pmt.getStartPeriod());
				changeList.put(pmt.getStartPeriod(), val);
			}
			int afterEnd = PeriodicPayment.addPeriods(pmt.getEndPeriod(), 1, PmtFreq.MONTHLY);
			if (changeList.containsKey(afterEnd)) {
				Change val = changeList.get(afterEnd);
				val.period = afterEnd;
				val.amount -= pmt.getMonthAmount(pmt.getEndPeriod());
			} else {
				Change val = new Change();
				val.period = afterEnd;
				val.amount = -pmt.getMonthAmount(pmt.getEndPeriod());
				changeList.put(afterEnd, val);
			}
		}
		return changeList;
	}
}
