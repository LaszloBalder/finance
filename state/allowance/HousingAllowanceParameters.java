package nl.yasmijn.state.allowance;

import java.math.BigDecimal;

public class HousingAllowanceParameters {
	private BigDecimal factorA;
	private BigDecimal factorB;
	private int minIncome;
	private int typeLimits;
	private double taskAmount;
	private double minNormRent;
	private double minBaseRent;

	public void setFactorA(BigDecimal factorA) {
		this.factorA = factorA;
	}

	public void setFactorB(BigDecimal factorB) {
		this.factorB = factorB;
	}

	public void setMinIncome(int minIncome) {
		this.minIncome = minIncome;
	}

	public void setTypeLimits(int typeLimits) {
		this.typeLimits = typeLimits;
	}

	public void setTaskAmount(double taskAmount) {
		this.taskAmount = taskAmount;
	}

	public void setMinNormRent(double minNormRent) {
		this.minNormRent = minNormRent;
	}

	public void setMinBaseRent(double minBaseRent) {
		this.minBaseRent = minBaseRent;
	}

	public BigDecimal getFactorA() {
		return factorA;
	}

	public BigDecimal getFactorB() {
		return factorB;
	}

	public int getMinIncome() {
		return minIncome;
	}

	public int getTypeLimits() {
		return typeLimits;
	}

	public double getTaskAmount() {
		return taskAmount;
	}

	public double getMinNormRent() {
		return minNormRent;
	}

	public double getMinBaseRent() {
		return minBaseRent;
	}
}
