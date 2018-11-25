package nl.yasmijn.state.tax;

import nl.yasmijn.utils.DateMath;

public class Reduction {
	private static final int pensionAge = 65;
	private static final int pensionBirthYear = 1946;
	// private static final int[] prePensionReduction = { 0, 0, 0, 0, 1200,
	// 1300, 1400 };
	private static final int[] labourReduMax = { 2362, 2362, 2362, 2100, 2100, 1838, 1838, 1838, 1574 };
	private static final double[] labourReduRate = { 0.18915, 0.18915, 0.18915, 0.16667, 0.16667, 0.14418, 0.14418, 0.14418,
			0.12152 };

	private static final double labourReduBaseRate = 0.01716;
	private static final int labourReductionIncomeThreshold = 9209;
	private static final int labourReductionIncomeTopThreshold = 44126;
	private static final int labourReductionIncomeReduMax = 77;
	private static final double labourReductionIncomeReduRate = 0.0125;

	private static final double pensLabReduBaseRate = 0.00785;
	private static final int pensLabReduMax = 1081;
	private static final double pensLabReduRate = 0.07921;
	private static final int pensLabReductionIncomeThreshold = 9209;
	private static final int pensLabReductionIncomeTopThreshold = 44126;
	private static final int pensLabReductionIncomeReduMax = 35;
	private static final double pensLabReductionIncomeReduRate = 0.00571;

	// Arbeidskorting
	public int getLabourReduction(int calcDate, int dateOfBirth, int income) {
		int age = DateMath.getAge(dateOfBirth, calcDate);
		if (age >= pensionAge) {
			return getPensionerLabourReduction(income);
		}
		int yearOfBirth = dateOfBirth / 10000;
		int offset = yearOfBirth - pensionBirthYear;
		if (offset > 8) {
			offset = 8;
		}
		double reduction = 0d;
		if (income > labourReductionIncomeThreshold) {
			reduction = labourReductionIncomeThreshold * labourReduBaseRate;
			reduction += (income - labourReductionIncomeThreshold) * labourReduRate[offset];
			reduction = Math.min(reduction, labourReduMax[offset]);
			System.out.println(reduction);
			if (income > labourReductionIncomeTopThreshold) {
				double reduRedu = (income - labourReductionIncomeTopThreshold) * labourReductionIncomeReduRate;
				reduction -= Math.min(reduRedu, labourReductionIncomeReduMax);
				System.out.println(reduction);
			}
		} else {
			reduction = income * labourReduBaseRate;
		}
		return (int) Math.round(reduction);
	}

	// Algemene heffingskorting
	public int getGeneralReduction(int dateOfBirth) {
		if (dateOfBirth < pensionAge) {
			return 1987;
		} else {
			return 910;
		}
	}

	// 65+ arbeidskorting
	private int getPensionerLabourReduction(int income) {
		double reduction = 0d;
		if (income > pensLabReductionIncomeThreshold) {
			reduction = pensLabReductionIncomeThreshold * pensLabReduBaseRate;
			reduction += (income - pensLabReductionIncomeThreshold) * pensLabReduRate;
			reduction = Math.min(reduction, pensLabReduMax);
			if (income > pensLabReductionIncomeTopThreshold) {
				double reduRedu = (income - pensLabReductionIncomeTopThreshold) * pensLabReductionIncomeReduRate;
				reduction -= Math.min(reduRedu, pensLabReductionIncomeReduMax);
			}
		} else {
			reduction = income * pensLabReduBaseRate;
		}
		return (int) Math.round(reduction);
	}

	// Doorwerkbonus
	public int getSeniorLabourReduction(int income) {
		double reduction = 0d;
		if (income > pensLabReductionIncomeThreshold) {
			reduction = pensLabReductionIncomeThreshold * pensLabReduBaseRate;
			reduction += (income - pensLabReductionIncomeThreshold) * pensLabReduRate;
			reduction = Math.min(reduction, pensLabReduMax);
			if (income > pensLabReductionIncomeTopThreshold) {
				double reduRedu = (income - pensLabReductionIncomeTopThreshold) * pensLabReductionIncomeReduRate;
				reduction -= Math.min(reduRedu, pensLabReductionIncomeReduMax);
			}
		} else {
			reduction = income * pensLabReduBaseRate;
		}
		return (int) Math.round(reduction);
	}

	// Jonggehandicapten korting
	public int getYoungDisabledLabourReduction(int dateOfBirth, int income) {
		return 696;
	}

	// Ouderenkorting
	public int getPensionerReduction(int dateOfBirth, int income) {
		if (income < 34857) {
			return 739;
		}
		return 0;
	}

	// Alleenstaande ouderenkorting
	public int getSinglePensionerReduction(int dateOfBirth, int income) {
		return 421;
	}

	// Levensloopkorting
	public int getSpecialLeaveReduction(int income) {
		return 201;
	}

	private static final int combinationThreshold = 4706;
	private static final int combinationBase = 775;
	private static final int combinationPensionerBase = 361;
	private static final double combinationRate = 0.038;
	private static final double combinationPensionerRate = 0.0177;
	private static final int combinationMax = 1859;
	private static final int combinationPensionerMax = 865;

	// Inkomensafhankelijke combinatiekorting
	public int getCombinationReduction(int calcDate, int dateOfBirth, int incomeFromLabour) {
		int base = combinationBase;
		double rate = combinationRate;
		int max = combinationMax;
		if (DateMath.getAge(dateOfBirth, calcDate) >= pensionAge) {
			base = combinationPensionerBase;
			rate = combinationPensionerRate;
			max = combinationPensionerMax;
		}
		double cReduction = 0d;
		if (incomeFromLabour > combinationThreshold) {
			cReduction = base + ((incomeFromLabour - combinationThreshold) * rate);
		}
		cReduction = Math.min(cReduction, max);

		return (int) Math.round(cReduction);
	}

	// Ouderschapsverlofkorting
	public int getParentingLeaveReduction(int totalHoursLeave) {
		return (int) (totalHoursLeave * 4.11);
	}

	// Alleenstaande ouder korting
	public int getSingleParentReduction(int calcDate, int dateOfBirth, int dateOfBirthYoungest) {
		if (DateMath.getAge(dateOfBirthYoungest, calcDate) > 16) {
			return 0;
		}
		if (DateMath.getAge(dateOfBirth, calcDate) >= pensionAge) {
			return 417;
		}
		return 931;
	}

	// Aanvullende alleenstaande ouder korting
	public int getAdditionalSingleParentReduction(int calcDate, int dateOfBirth, int dateOfBirthYoungest, int incomeFromLabour) {
		if (DateMath.getAge(dateOfBirthYoungest, calcDate) > 27) {
			return 0;
		}
		double reduction = 0.043 * incomeFromLabour;
		if (DateMath.getAge(dateOfBirth, calcDate) >= pensionAge) {
			reduction = Math.min(reduction, 417);
		} else {
			reduction = Math.min(reduction, 1523);
		}
		return (int) Math.round(reduction);
	}

	// Korting maatschappelijk beleggen
	public int getSocialInvestmentReduction(int investment) {
		return (int) (investment * 0.01);
	}

	// Korting durfkapitaal/groen beleggen
	public int getVentureCapitalGreenReduction(int investment) {
		return (int) (investment * 0.01);
	}

	public static void main(String[] args) {
		Reduction r = new Reduction();
		System.out.println(r.getLabourReduction(20110325, 19650114, 45000));
		System.out.println(r.getCombinationReduction(20110325, 19650114, 26000));

	}
}
