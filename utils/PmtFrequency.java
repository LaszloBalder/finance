package nl.yasmijn.utils;

public enum PmtFrequency {
//	CUSTOM(0),
	ANNUALLY(1),
	SEMIANNUALLY(2),
	QUARTERLY(4),
	BIMONTHLY(6),
	MONTHLY(12),
	FOURWEEKLY(13),
	BIWEEKLY(26),
	WEEKLY(52);
	
	private final int fraction;
	
	private PmtFrequency(int yearFraction) {
		this.fraction = yearFraction;
	}
	
	public int getFraction() {
		return fraction;
	}
	
	public int addPeriods(int period, int periods) {
		int temp = period % 100 + periods - 1;
		return ((period / 100) * 100) + ((temp / fraction) * 100) + ((temp % fraction) + 1);
	}
}