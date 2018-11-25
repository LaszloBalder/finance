package test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import nl.yasmijn.state.tax.TaxSystem;

public class MortgagePlan {
	private List<Person> persons = new ArrayList<Person>();
	private int startYear;
	private final int maxHorizon = 50;
	private int proprtyTaxValue = 0;
	private TaxSystem taxSystem;
	Obligation obligation = null;
	List<PeriodicPayment> incomeList = new ArrayList<PeriodicPayment>();

	public MortgagePlan(int startYear, TaxSystem taxSystem) {
		this.startYear = startYear;
		this.taxSystem = taxSystem;
	}

	public Person createPerson(Sex sex, int dateOfBirth) {
		Person person = new Person(sex, dateOfBirth);
		persons.add(person);
		return person;
	}

	public int getProprtyTaxValue() {
		return proprtyTaxValue;
	}

	public void setProprtyTaxValue(int proprtyTaxValue) {
		this.proprtyTaxValue = proprtyTaxValue;
	}

	public int getStartYear() {
		return startYear;
	}

	public Obligation getObligation() {
		return obligation;
	}

	public void setObligation(Obligation obligation) {
		this.obligation = obligation;
	}

	public void addMonthlyIncome(int monthIncome, int startMonth, int endMonth) {
		Income income = new Income(startMonth, endMonth, monthIncome, PmtFreq.MONTHLY);
		incomeList.add(income);
	}

	public List<IncomeTaxInfo> getIncomeTaxInfo() {
		List<IncomeTaxInfo> itiList = getIncomeYearInfo();
		int notRentValue = taxSystem.getNotionalRentalValue(proprtyTaxValue);
		for (IncomeTaxInfo iti : itiList) {
			if (iti.getIncome() > 0) {
				if (iti.getInterestPayed() > notRentValue) {
					int normalTax = taxSystem.getIncomeTax((int) iti.getIncome());
					int reducedTax = taxSystem.getIncomeTax((int) (iti.getIncome() - (iti.getInterestPayed() - notRentValue)));
					if (reducedTax < normalTax) {
						iti.setTaxBenefit(normalTax - reducedTax);
					}
				}
			}
		}
		return itiList;
	}

	private void setLoanValues(List<IncomeTaxInfo> itiList) {
		int prevPrincipal = obligation.getPrincipal();
		int totalPayment = 0;
		int currPrincipal = 0;
		for (int year = startYear; year < startYear + maxHorizon; year++) {
			IncomeTaxInfo iti = new IncomeTaxInfo(year);
			totalPayment = (int) obligation.getYearAmount(year);
			if (totalPayment > 0) {
				currPrincipal = (int) obligation.getPrincipalAtYearEnd(year);
				iti.setDownPayment(prevPrincipal - currPrincipal);
				iti.setInterestPayed(totalPayment - iti.getDownPayment());
				prevPrincipal = currPrincipal;
			}
			itiList.add(iti);
		}
	}

	private List<IncomeTaxInfo> createIncomeTaxInfoList() {
		List<IncomeTaxInfo> incList = new ArrayList<IncomeTaxInfo>(maxHorizon);
		for (int year = startYear; year < startYear + maxHorizon; year++) {
			incList.add(new IncomeTaxInfo(year));
		}
		return incList;
	}

	private List<IncomeTaxInfo> getIncomeYearInfo() {
		int currPerson = 0;
		List<IncomeTaxInfo> incList = createIncomeTaxInfoList();
		for (Person person : persons) {
			currPerson++;
			SortedMap<Integer, Change> changeList = PeriodicPayment.getChangeList(person.getIncomeList());
			Integer previous = changeList.firstKey();
			double currAmount = changeList.get(previous).amount;
			for (Integer periodEnd : changeList.keySet()) {
				if (periodEnd == changeList.firstKey()) {
					continue;
				}
				for (int currPeriod = previous; currPeriod < periodEnd; currPeriod = PeriodicPayment.addPeriods(currPeriod, 1,
						PmtFreq.MONTHLY)) {
					//incList.get((currPeriod / 100) - startYear).getIncomeAdder(AddType.INC1).add((int) currAmount);
				}
				previous = periodEnd;
				currAmount += changeList.get(periodEnd).amount;
			}
		}
		return incList;
	}
}
