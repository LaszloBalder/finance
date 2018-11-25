package test;

public enum Sex {
	MALE(1), FEMALE(2);

	private int typeId;

	private Sex(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}
}
