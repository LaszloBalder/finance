package nl.yasmijn.borrowingcapacity;

public class Identification {
	private String issuedBy;
	private String issuedAt;
	private int validFrom;
	private int validThru;
	private String Number;
	private IdentificationType idType;

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(String issuedAt) {
		this.issuedAt = issuedAt;
	}

	public int getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(int validFrom) {
		this.validFrom = validFrom;
	}

	public int getValidThru() {
		return validThru;
	}

	public void setValidThru(int validThru) {
		this.validThru = validThru;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public IdentificationType getIdType() {
		return idType;
	}

	public void setIdType(IdentificationType idType) {
		this.idType = idType;
	}

}
