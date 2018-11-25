package nl.yasmijn.banking;

public class MonetaryAmount {
	private final static double ONE_HUNDRED = 100.0;
	int amountInCents = 0;

	public MonetaryAmount() {
		amountInCents = 0;
	}

	public MonetaryAmount(double amount) {
		amountInCents = (int) Math.round(amount * ONE_HUNDRED);
	}

	private MonetaryAmount(int amountInCents) {
		this.amountInCents = amountInCents;
	}

	public double getAmount() {
		return amountInCents / ONE_HUNDRED;
	}

	public void add(MonetaryAmount amount) {
		this.amountInCents += amount.amountInCents;
	}

	public void add(double amount) {
		this.amountInCents += Math.round(amount * ONE_HUNDRED);
	}

	public void substract(MonetaryAmount amount) {
		this.amountInCents -= amountInCents;
	}

	public void substract(double amount) {
		this.amountInCents -= Math.round(amount * ONE_HUNDRED);
	}

	public void multiplyBy(double factor) {
		this.amountInCents = (int) Math.round(this.amountInCents * factor);
	}
}
