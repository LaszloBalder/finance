package nl.yasmijn.borrowingcapacity;

import java.util.Date;
import java.util.List;

public class Household {
	private List<Householder> householders;
	private LegalType legalType;
	private List<BenefitChild> boarders;
	private ContactData contact;
	private Address address;

	public List<Householder> getHouseholders() {
		return householders;
	}

	public void setHouseholders(List<Householder> householders) {
		this.householders = householders;
	}

	public LegalType getLegalType() {
		return legalType;
	}

	public void setLegalType(LegalType legalType) {
		this.legalType = legalType;
	}

	public List<BenefitChild> getBoarders() {
		return boarders;
	}

	public void setBoarders(List<BenefitChild> boarders) {
		this.boarders = boarders;
	}

	public ContactData getContact() {
		return contact;
	}

	public void setContact(ContactData contact) {
		this.contact = contact;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getIncomeAboveAge(int age, Date calcDate, int threshold) {
		int totalIncome = 0;
		for (Householder holder : householders) {
			if (holder.getAge(calcDate) >= age) {
				totalIncome += holder.getIncome();
			}
		}
		return totalIncome;
	}
}
