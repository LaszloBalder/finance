package nl.yasmijn.borrowingcapacity;

public enum IdentificationType {

	DriversLicense(1), IdCard(2), Passport(3);
	private int typeId;

	private IdentificationType(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}
}
