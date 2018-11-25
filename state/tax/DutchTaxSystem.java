package nl.yasmijn.state.tax;

import nl.yasmijn.utils.SmallDate;

public class DutchTaxSystem {
	public int getPensionAge(int dateOfBirth) {
		return getPensionAgeByYearOfBirth(dateOfBirth / 10000);
	}

	public int getPensionAge(SmallDate dateOfBirth) {
		return getPensionAgeByYearOfBirth(dateOfBirth.getYear());
	}
	
	private int getPensionAgeByYearOfBirth(int yearOfBirth)
	{
		if (yearOfBirth >= 1960) {
			return 67;
		} else if (yearOfBirth >= 1955) {
			return 66;
		} else {
			return 65;
		}
	}

	public static void main(String[] args) {
		System.out.println(new DutchTaxSystem().getPensionAge(19650114));
		System.out.println(new DutchTaxSystem().getPensionAge(19550114));
		System.out.println(new DutchTaxSystem().getPensionAge(19541231));
		System.out.println(new DutchTaxSystem().getPensionAge(new SmallDate(1965, 01, 14)));
		System.out.println(new DutchTaxSystem().getPensionAge(new SmallDate(1955, 01, 14)));
		System.out.println(new DutchTaxSystem().getPensionAge(new SmallDate(1954, 12, 31)));
	}
}
