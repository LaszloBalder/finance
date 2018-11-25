package nl.yasmijn.state.tax;

import java.util.ArrayList;
import java.util.List;

public class TaxSystem {
	public int getDeductableInterest(int interestPayed, int propertyTaxValue) {
		int notionalRentalValue = getNotionalRentalValue(propertyTaxValue);
		if (notionalRentalValue < interestPayed) {
			return interestPayed - notionalRentalValue;
		}
		return 0;
	}

	public int getNotionalRentalValue(int propertyTaxValue) {
		for (NotionalRentalTaxBracket bracket : rentalTariffs) {
			if (propertyTaxValue >= bracket.getLowerBound() && propertyTaxValue <= bracket.getUpperBound()) {
				return (int) (propertyTaxValue * bracket.getTariff() + bracket.getAdditionalAmount());
			}
		}
		return 0;
	}

	public int getIncomeTax(int income) {
		return getIncomeTax(income, 35);
	}

	public int getIncomeTax(int income1, int income2, int age1, int age2, int deductable) {
		int highestIncome = income1 > income2 ? income1 : income2;
		int lowestIncome = income1 + income2 - highestIncome;
		int highIncomeAge = highestIncome == income1 ? age1 : age2;
		int lowIncomeAge = age1 + age2 - highIncomeAge;

		if (lowestIncome == 0 || lowIncomeAge == 0) {
			return getIncomeTax(income1 - deductable, age1);
		}

		if ((age1 < 65 && age2 < 65) || (age1 >= 65 && age2 >= 65)) {
			if (highestIncome > lowestIncome + deductable) {
				return getIncomeTax(highestIncome - deductable, highIncomeAge) + getIncomeTax(lowestIncome, lowIncomeAge);
			} else {
				return 2 * getIncomeTax((highestIncome + lowestIncome - deductable) / 2, highIncomeAge);
			}
		} else {
			if (highIncomeAge < 65) {
				return getIncomeTax(highestIncome - deductable, highIncomeAge)
						+ getIncomeTax(lowestIncome, lowIncomeAge);
			} else if (highestIncome > pensionerTreshold) {
				int highIncDedect = highestIncome - pensionerTreshold;
				return getIncomeTax(highestIncome - highIncDedect, highIncomeAge)
						+ getIncomeTax(lowestIncome - deductable + highIncDedect, lowIncomeAge);
			} else {
				int correction = lowestIncome < deductable ? deductable - lowestIncome : 0;
				return getIncomeTax(lowestIncome - deductable + correction, lowIncomeAge)
						+ getIncomeTax(highestIncome - correction, highIncomeAge);
			}
		}
	}

	public int getIncomeTax(int income, int age) {
		double incomeTax = 0;
		if (income <= 1) {
			return 0;
		}
		for (IncomeTaxBracket bracket : brackets) {
			if (income > bracket.getLowerBound()) {
				if (income <= bracket.getUpperBound()) {
					incomeTax += (income - bracket.getLowerBound()) * bracket.getTaxRate(age >= 65);
					break;
				} else {
					incomeTax += (bracket.getUpperBound() - bracket.getLowerBound()) * bracket.getTaxRate(age >= 65);
				}
			} else {
				break;
			}
		}
		return (int) incomeTax;
	}

	private List<IncomeTaxBracket> brackets = new ArrayList<IncomeTaxBracket>();

	private int pensionerTreshold = 0;

	public void addTaxBracket(int lowerBound, int upperBound, double normTariff, double specialTariff) {
		if ((lowerBound == 0 && brackets.size() == 0)
				|| (brackets.size() > 0 && lowerBound == brackets.get(brackets.size() - 1).getUpperBound())) {
			if (Math.abs(normTariff - specialTariff) > 0.01) {
				pensionerTreshold = upperBound;
			}
			IncomeTaxBracket bracket = new IncomeTaxBracket();
			bracket.setLowerBound(lowerBound);
			bracket.setUpperBound(upperBound);
			bracket.setTaxRate(normTariff);
			bracket.setSpecialTaxRate(specialTariff);
			brackets.add(bracket);
		} else {
			System.out.println("Not added" + lowerBound);
		}
	}

	private List<NotionalRentalTaxBracket> rentalTariffs = new ArrayList<NotionalRentalTaxBracket>();

	public void addNotionalRentalTaxTariff(int lowerBound, int upperBound, double tariff) {
		addNotionalRentalTaxTariff(lowerBound, upperBound, tariff, 0);
	}

	public void addNotionalRentalTaxTariff(int lowerBound, int upperBound, double tariff, int additionalAmount) {
		if ((lowerBound == 0 && rentalTariffs.size() == 0)
				|| (rentalTariffs.size() > 0 && lowerBound == rentalTariffs.get(rentalTariffs.size() - 1).getUpperBound() + 1)) {
			NotionalRentalTaxBracket rentalBracket = new NotionalRentalTaxBracket();
			rentalBracket.setLowerBound(lowerBound);
			rentalBracket.setUpperBound(upperBound);
			rentalBracket.setTariff(tariff);
			rentalBracket.setAdditionalAmount(additionalAmount);
			rentalTariffs.add(rentalBracket);
		}
	}
}
