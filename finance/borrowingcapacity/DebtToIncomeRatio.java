package nl.yasmijn.finance.borrowingcapacity;

import java.util.SortedMap;
import java.util.TreeMap;

import nl.yasmijn.utils.RangeKey;

public class DebtToIncomeRatio {
	private SortedMap<RangeKey, ChfDti> nonPensionerDtis = new TreeMap<RangeKey, ChfDti>();
	private ChfDti prev = null;

	public void addChfDti(int from, double ratio1, double ratio2, double ratio3, double ratio4, double ratio5) {
		ChfDti norm = new ChfDti();
		norm.lower = from;
		norm.ratio1 = ratio1;
		norm.ratio2 = ratio2;
		norm.ratio3 = ratio3;
		norm.ratio4 = ratio4;
		norm.ratio5 = ratio5;
		if (prev == null) {
			prev = norm;
			return;
		}
		prev.upper = from - 1;
		nonPensionerDtis.put(new RangeKey(prev.lower, prev.upper), prev);
		prev = norm;
	}

	private SortedMap<RangeKey, ChfDti> pensionerDtis = new TreeMap<RangeKey, ChfDti>();
	private ChfDti prev65 = null;

	public void addChfDti65(int from, double ratio1, double ratio2, double ratio3, double ratio4, double ratio5) {
		ChfDti norm = new ChfDti();
		norm.lower = from;
		norm.ratio1 = ratio1;
		norm.ratio2 = ratio2;
		norm.ratio3 = ratio3;
		norm.ratio4 = ratio4;
		norm.ratio5 = ratio5;
		if (prev65 == null) {
			prev65 = norm;
			return;
		}
		prev65.upper = from - 1;
		pensionerDtis.put(new RangeKey(prev65.lower, prev65.upper), prev65);
		prev65 = norm;
	}

	public double getDti(int age, int income, double rate) {
		SortedMap<RangeKey, ChfDti> dtis = nonPensionerDtis;
		if (age >= 65) {
			dtis = pensionerDtis;
		}
		int ratePoints = (int) Math.round(10000 * rate);
		if (ratePoints <= 500)
			return dtis.get(new RangeKey(income)).ratio1;
		else if (ratePoints >= 501 && ratePoints <= 550)
			return dtis.get(new RangeKey(income)).ratio2;
		else if (ratePoints >= 551 && ratePoints <= 600)
			return dtis.get(new RangeKey(income)).ratio3;
		else if (ratePoints >= 601 && ratePoints <= 650)
			return dtis.get(new RangeKey(income)).ratio4;
		else
			return dtis.get(new RangeKey(income)).ratio5;
	}

}
