package nl.yasmijn.borrowingcapacity;

public enum StateUnion {
	EuropeanUnion(1), BritishCommonwealth(2), BeNeLux(3), Schengen(4), OESO(5), EuropeanEconomicArea(6), EuropeanFreeTradeAssociation(
			7), OECD(8);
	private int typeId;

	private StateUnion(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}
}
