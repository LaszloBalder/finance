package nl.yasmijn.borrowingcapacity;

public enum AllowanceHouseholdType {
	Single(1), Multi(2), Single65Plus(3), Multi65Plus(4);
	private int typeId;

	private AllowanceHouseholdType(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}
}
