package nl.yasmijn.borrowingcapacity;

public class Deposit {
	private int period;
	private int amount;
	private boolean atStart;

	public boolean isAtStart() {
		return atStart;
	}

	public void setAtStart(boolean atStart) {
		this.atStart = atStart;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
