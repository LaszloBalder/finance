package nl.yasmijn.banking;

import java.util.ArrayList;
import java.util.List;

import nl.yasmijn.utils.SmallDate;

public class Account {
	private MonetaryAmount balance = new MonetaryAmount();
	// private List<double> transactions = new Order
	private List<Transaction> transactions = new ArrayList<Transaction>();

	public void credit(double amount, SmallDate transactionDate) {
		Transaction transaction = new Transaction(amount, true, transactionDate);
		balance.add(amount);
		if (transactions.size() == 0) {
			transactions.add(transaction);
		} else if (transaction.getDate().toIsoDate() < transactions.get(0).getDate().toIsoDate()) {
			transactions.add(0, transaction);
		} else if (transaction.getDate().toIsoDate() > transactions.get(transactions.size() - 1).getDate().toIsoDate()) {
			transactions.add(transaction);
		} else {
			//TODO: has to be tested.
			//TODO: start at the end.
			int index = 0;
			while (transaction.getDate().toIsoDate() < transactions.get(++index).getDate().toIsoDate()) {
			}
			transactions.add(index, transaction);
		}
	}

	public SmallDate getBalanceDate() {
		return transactions.get(transactions.size() - 1).getDate();
	}
	
	public MonetaryAmount getBalance(SmallDate date) {
		int index = transactions.size() - 1;
		MonetaryAmount requiredBalance = new MonetaryAmount(balance.getAmount());
		while (transactions.get(index).getDate().toIsoDate() > date.toIsoDate()) {
			requiredBalance.substract(transactions.get(index--).getAmount());
		}
		return requiredBalance;
	}
}
