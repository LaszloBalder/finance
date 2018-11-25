package test;

public class IncomeTaxInfo {
	private int year = 0;
	private int ageAtStart = 0;
	private double oblAmount = 0;
	private double income1 = 0;
	private double income2 = 0;
	private double interestPayed = 0;
	private int taxBenefit = 0;
	private int downPayment = 0;
	private Adder inc1 = new AddIncome1();
	private Adder inc2 = new AddIncome2();
	private Adder oblAdder = new AddObligation();

	public int getYear() {
		return year;
	}

	public double getObligationAmount() {
		return oblAmount;
	}

	public IncomeTaxInfo(int year) {
		this.year = year;
	}

	public int getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(int downPayment) {
		this.downPayment = downPayment;
	}

	public int getAgeAtStart() {
		return ageAtStart;
	}

	public void setAgeAtStart(int ageAtStart) {
		this.ageAtStart = ageAtStart;
	}

	public double getIncome() {
		return income1;
	}

	public void setIncome(double income) {
		this.income1 = income;
	}

	public double getIncome2() {
		return income2;
	}

	public void setIncome2(double income) {
		this.income2 = income;
	}

	public double getTotalIncome() {
		return income1 + income2;
	}

	public double getInterestPayed() {
		return interestPayed;
	}

	public void setInterestPayed(double interestPayed) {
		this.interestPayed = interestPayed;
	}

	public int getTaxBenefit() {
		return taxBenefit;
	}

	public void setTaxBenefit(int taxBenefit) {
		this.taxBenefit = taxBenefit;
	}

	public void addValue(int value, AddType addType) {
		if (addType == AddType.INC1)
			this.income1 = value;
		else if (addType == AddType.INC2)
			this.income2 = value;
		else if (addType == AddType.OBL)
			this.oblAmount = value;
		else
			throw new IllegalArgumentException();
	}

	public Adder Adder(AddType type) {
		if (type == AddType.INC1)
			return inc1;
		else if (type == AddType.INC2)
			return inc2;
		else if (type == AddType.OBL)
			return oblAdder;
		else
			throw new IllegalArgumentException();
	}

	class AddIncome1 implements Adder {
		public void add(int income) {
			income1 += income;
		}
	}

	class AddIncome2 implements Adder {
		public void add(int income) {
			income2 += income;
		}
	}

	class AddObligation implements Adder {
		public void add(int obl) {
			oblAmount += obl;
		}
	}
}

interface Adder {
	void add(int income);
}
