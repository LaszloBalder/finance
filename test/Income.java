package test;

public class Income extends PeriodicPayment {

	private int incomeType;
	private String description = null;
	private TaxType taxType;

	public Income(int startPeriod, int endPeriod, int amount, PmtFreq pmtFreq) {
		super(startPeriod, endPeriod, amount, pmtFreq);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(int incomeType) {
		this.incomeType = incomeType;
	}
}
