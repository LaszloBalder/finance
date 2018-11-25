package nl.yasmijn.borrowingcapacity;

import java.util.Date;

public class BenefitChild {
	private Identity identity;
	private ContactData contact;
	private Address personalAddress;

	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	public ContactData getContact() {
		return contact;
	}

	public void setContact(ContactData contact) {
		this.contact = contact;
	}

	public Address getPersonalAddress() {
		return personalAddress;
	}

	public void setPersonalAddress(Address personalAddress) {
		this.personalAddress = personalAddress;
	}
	
	public int getAge(Date calcDate) {
		return 0;
	}

	public int getTotalIncome() {
		// TODO Auto-generated method stub
		return 0;
	}

}
