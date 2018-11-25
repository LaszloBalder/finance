package nl.yasmijn.borrowingcapacity;

import java.util.List;

public class Householder extends BenefitChild {
	private List<Income> incomes;
	private int preferedPendionDate;
	private List<BenefitChild> children;
	private Relation previousRelation;
	private List<PeriodAbroad> abroad;
	private Address address;
	private Address previousAddress;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getPreviousAddress() {
		return previousAddress;
	}

	public void setPreviousAddress(Address previousAddress) {
		this.previousAddress = previousAddress;
	}

	public List<Income> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<Income> incomes) {
		this.incomes = incomes;
	}

	public int getPreferedPendionDate() {
		return preferedPendionDate;
	}

	public void setPreferedPendionDate(int preferedPendionDate) {
		this.preferedPendionDate = preferedPendionDate;
	}

	public List<BenefitChild> getChildren() {
		return children;
	}

	public void setChildren(List<BenefitChild> children) {
		this.children = children;
	}

	public Relation getPreviousRelation() {
		return previousRelation;
	}

	public void setPreviousRelation(Relation previousRelation) {
		this.previousRelation = previousRelation;
	}

	public List<PeriodAbroad> getAbroad() {
		return abroad;
	}

	public void setAbroad(List<PeriodAbroad> abroad) {
		this.abroad = abroad;
	}

	public int getIncome() {
		int totalIncome = 0;
		for (Income income : incomes) {
			totalIncome += income.getAmount();
		}
		return totalIncome;
	}
}
