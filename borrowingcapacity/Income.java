package nl.yasmijn.borrowingcapacity;

import nl.yasmijn.utils.PmtFrequency;

public class Income extends Cashflow {
	public Income(int startPeriod, int endPeriod, double amount, PmtFrequency pmtFreq) {
		super(startPeriod, endPeriod, amount, pmtFreq);
	}

	private PensionArrangement pensionArrangement;

	public PensionArrangement getPensionArrangement() {
		return pensionArrangement;
	}

	public void setPensionArrangement(PensionArrangement pensionArrangement) {
		this.pensionArrangement = pensionArrangement;
	}
}
