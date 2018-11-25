package nl.yasmijn.party;

public enum PaymentFrequency {
	CUSTOM(0),
	ANNUALLY(1),
	SEMIANNUALLY(2),
	QUARTERLY(4),
	BIMONTHLY(6),
	MONTHLY(12),
	FOURWEEKLY(13),
	BIWEEKLY(26),
	WEEKLY(52);
	
	private final int yearFraction;
	
	PaymentFrequency(int yearFraction) {
		this.yearFraction = yearFraction;
	}
	
	public int getTearFraction() {
		return yearFraction;
	}
}
