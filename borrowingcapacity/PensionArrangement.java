package nl.yasmijn.borrowingcapacity;

public enum PensionArrangement {
	LastLoan(1), AverageLoan(2), AvailablePremium(3), None(9);
	private int typeId;

	private PensionArrangement(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}
}
