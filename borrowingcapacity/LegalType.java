package nl.yasmijn.borrowingcapacity;

public enum LegalType {
	Gemeenschap(1), BeperkteGemeenschap(3), KoudeUitsluiting(3), Verrekenstelsel(
			4), Geen(9);
	private int typeId;

	private LegalType(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}
}
