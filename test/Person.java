package test;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private Sex sex;
	private int dateOfBirth;
	private List<Income> incomes = new ArrayList<Income>();
	private List<Obligation> obligations = new ArrayList<Obligation>();

	public Person(Sex sex, int dateOfBirth) {
		this.sex = sex;
		this.dateOfBirth = dateOfBirth;
	}

	public void addMonthlyIncome(int monthIncome, int startMonth, int endMonth) {
		Income income = new Income(startMonth, endMonth, monthIncome, PmtFreq.MONTHLY);
		incomes.add(income);
	}

	public Sex getSex() {
		return sex;
	}

	public int getDateOfBirth() {
		return dateOfBirth;
	}

	public List<Income> getIncomeList() {
		return incomes;
	}

	public List<Obligation> getObligations() {
		return obligations;
	}

	public void setObligations(List<Obligation> obligations) {
		this.obligations = obligations;
	}
}
