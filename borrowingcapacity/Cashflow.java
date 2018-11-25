package nl.yasmijn.borrowingcapacity;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import nl.yasmijn.utils.PmtFrequency;

public class Cashflow {
	private int startPeriod;
	private int altStartPeriod;
	private int endPeriod;
	private int altEndPeriod;
	private double amount;
	private int duration;
	private PmtFrequency pmtFreq;
	private boolean isConstant;

	public Cashflow(int startPeriod, int endPeriod, double amount, PmtFrequency pmtFreq) {
		if (!isValidPeriod(startPeriod, pmtFreq) || !isValidPeriod(endPeriod, pmtFreq) || amount < 1 || startPeriod > endPeriod
				|| pmtFreq.getFraction() == 0) {
			throw new IllegalArgumentException();
		}
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
		this.amount = amount;
		this.pmtFreq = pmtFreq;
		this.duration = getPeriodsBetween(startPeriod, endPeriod, pmtFreq);
		this.isConstant = true;
	}
	
	public boolean isConstant() {
		return isConstant;
	}

	public void resetAlternativeDates() {
		altStartPeriod = 0;
		altEndPeriod = 0;
	}
	
	public PmtFrequency getPmtFreq() {
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
		if (altEndPeriod != 0) {
			return altEndPeriod;
		} else {
			return endPeriod;
		}
	}

	public int getStartPeriod() {
		if (altStartPeriod != 0) {
			return altStartPeriod;
		} else {
			return startPeriod;
		}
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
		if (period >= getStartPeriod() && period <= getEndPeriod()) {
			return amount;
		} else {
			return 0.0;
		}
	}

	public double getAmount(int startQueryPeriod, int endQueryPeriod) {
		if (!isValidPeriod(startQueryPeriod, pmtFreq) || !isValidPeriod(endQueryPeriod, pmtFreq)) {
			throw new IllegalArgumentException();
		}
		if (startQueryPeriod > getEndPeriod() || endQueryPeriod < getStartPeriod()) {
			return 0;
		}
		int start = startQueryPeriod > getStartPeriod() ? startQueryPeriod : getStartPeriod();
		int end = endQueryPeriod < getEndPeriod() ? endQueryPeriod : getEndPeriod();
		return getPeriodsBetween(start, end, pmtFreq) * amount;
	}

	protected static boolean isValidPeriod(int yearPeriod, PmtFrequency pmtFreq) {
		if (yearPeriod / 100 < 1900 || yearPeriod / 100 > 9999)
			return false;
		int period = yearPeriod % 100;
		return (period > 0 && period <= pmtFreq.getFraction());
	}

	public static int getPeriodsBetween(int start, int end, PmtFrequency pmtFreq) {
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


	public static SortedMap<Integer, Change> getChangeList(List<? extends Cashflow> pmtList) {
		SortedMap<Integer, Change> changeList = new TreeMap<Integer, Change>();
		for (Cashflow pmt : pmtList) {
			if (changeList.containsKey(pmt.getStartPeriod())) {
				Change val = changeList.get(pmt.getStartPeriod());
				val.amount += pmt.getMonthAmount(pmt.getStartPeriod());
			} else {
				Change val = new Change();
				val.period = pmt.getStartPeriod();
				val.amount = pmt.getMonthAmount(pmt.getStartPeriod());
				changeList.put(pmt.getStartPeriod(), val);
			}
			int afterEnd = PmtFrequency.MONTHLY.addPeriods(pmt.getEndPeriod(), 1);
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
