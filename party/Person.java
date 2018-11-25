package nl.yasmijn.party;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person { // should be person
	private int relationNumber;
	private String name;
	private List<Benefit> incomeSources = new ArrayList<Benefit>();
	private List<Duty> duties = new ArrayList<Duty>();
}

class PreviousRelation {
	private String relationType;
	private Date endedOn;
	private String endedType;
}

class CurrentRelation {
	private String relationtype;
	private Date startedOn;
	private Person with;
}

class Duty {

	private List<Payment> payments = new ArrayList<Payment>();
}

class Allemony extends Duty {

}

class Loan extends Duty {

}

class CreditFacility extends Duty {

}

abstract class Benefit {
	public abstract int getYearIncome();

	private List<Payment> revenues = new ArrayList<Payment>();

}

class Work extends Benefit {
	public int getYearIncome() {
		return 0;
	}

}

class Allowance extends Benefit {
	public int getYearIncome() {
		return 0;
	}

}

class Asset extends Benefit {
	public int getYearIncome() {
		return 0;
	}

}

class Payment {
	private PaymentFrequency frequency;
	private int amount;
	private int weight;
	private int incomeType;
	private Date startDate;
	private Date endDate;

}

class PartyGroup {
	private List<Person> persons = new ArrayList<Person>();
	private List<Duty> duties = new ArrayList<Duty>();
	private String name;

	public int getYearIncome() {
		return 0;
	}
}

class Company {
	private List<Person> owners = new ArrayList<Person>();
	private int legalEntityType = 0; // NV, BV, VoF, ZZP
	private List<BalanceSheet> balanceSheets = new ArrayList<BalanceSheet>();

}

class BalanceSheet {
	private int amount;
	private int year;
	private String AccordedBy;
}