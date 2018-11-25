package nl.yasmijn.borrowingcapacity;

import java.math.BigDecimal;

public class PensionRight {
	private int startDate;
	private BigDecimal amount;

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
