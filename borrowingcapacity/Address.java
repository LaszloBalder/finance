package nl.yasmijn.borrowingcapacity;

public class Address {
	private String postalCode;
	private String street;
	private String houseNo;
	private String houseNoSuffix;
	private String city;
	private Country country;

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getHouseNoSuffix() {
		return houseNoSuffix;
	}

	public void setHouseNoSuffix(String houseNoSuffix) {
		this.houseNoSuffix = houseNoSuffix;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
