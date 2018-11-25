package nl.yasmijn.borrowingcapacity;

public enum ObligationType {
	RevolvingCredit(1), PersonalLoan(2);
	private int id;

	private ObligationType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
