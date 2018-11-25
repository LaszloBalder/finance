package test;

public enum PmtFreq {
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
	
	private PmtFreq(int yearFraction) {
		this.fraction = yearFraction;
	}
	
	public int getFraction() {
		return fraction;
	}
}
