package nl.yasmijn.banking;

import nl.yasmijn.utils.SmallDate;

public class Transaction {
	private boolean credit = false;
	private MonetaryAmount amount;
	private SmallDate transactionDate;
	private SmallDate valueDate;

	public Transaction(double amount, boolean credit, SmallDate date) {
		this.amount.add(amount);
		this.credit = credit;
		this.transactionDate = date;
	}

	public boolean isCredit() {
		return credit;
	}

	public void setCredit(boolean credit) {
		this.credit = credit;
	}

	public MonetaryAmount getAmount() {
		return amount;
	}

	public void setAmount(MonetaryAmount amount) {
		this.amount = amount;
	}

	public SmallDate getDate() {
		return transactionDate;
	}

	public void setDate(SmallDate date) {
		this.transactionDate = date;
	}

	public SmallDate getValueDate() {
		return valueDate;
	}

	public void setValueDate(SmallDate date) {
		this.valueDate = date;
	}
}
