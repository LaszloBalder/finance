package nl.yasmijn.state.allowance;

import java.util.Date;
import java.util.GregorianCalendar;

import nl.yasmijn.borrowingcapacity.BenefitChild;

public class ChildBenefits {
	private final static double[] benefitBefore95 = { 278.55, 313.25, 324.81, 350.23, 365.47, 375.64, 382.90, 396.22, 406.57,
			414.85 };
	private final static double benefit_0_5 = 194.99;
	private final static double benefit_6_11 = 236.77;
	private final static double benefit_12_16 = 278.55;
	private final static double[] childBudget = { 1011.0, 1466.0, 1826.0, 2110.0, 2299.0 };
	private final static double budgetOver5PerChild = 189.0;
	private final static double budget12_15PerChild = 231.0;
	private final static double budget16_17PerChild = 296.0;
	private final static double budgetReductionThreshold = 28897.0;
	private final static double budgetReductionRate = 0.076;

	private ChildBenefits() {
	}

	// http://www.toeslagen.nl/particulier/kindgebonden_budget.html
	public int getChildBudget(int aggregateAssessmentIncome, BenefitChild[] children, int year) {
		Date calcDate = new GregorianCalendar(year, GregorianCalendar.JANUARY, 1).getTime();
		double totalBudget = 0;
		int below12 = 0;
		int between12_15 = 0;
		int between16_17 = 0;
		for (BenefitChild child : children) {
			int age = child.getAge(calcDate);
			if (age < 12) {
				below12++;
			} else if (age < 16) {
				between12_15++;
			} else if (age < 18) {
				between16_17++;
			}
		}
		int budgetChildren = below12 + between12_15 + between16_17;
		if (budgetChildren == 0) {
			return 0;
		}
		if (budgetChildren > 5) {
			totalBudget = childBudget[4];
			totalBudget += ((budgetChildren - 5) * budgetOver5PerChild);
		} else {
			totalBudget = childBudget[budgetChildren - 1];
		}
		totalBudget += (between12_15 * budget12_15PerChild);
		totalBudget += (between16_17 * budget16_17PerChild);

		if (aggregateAssessmentIncome > budgetReductionThreshold) {
			totalBudget -= ((aggregateAssessmentIncome - budgetReductionThreshold) * budgetReductionRate);
		}
		if (totalBudget < 0.0) {
			totalBudget = 0.0;
		}
		return (int) Math.round(totalBudget);
	}

	public int getChildBenefit(BenefitChild[] children, int year, int quarter) {
		Date calcDate = new GregorianCalendar(year, (quarter * 3) - 2, 1).getTime();
		int childrenUnderBenefit = 0;
		for (BenefitChild child : children) {
			childrenUnderBenefit += (child.getAge(calcDate) < 19 ? 1 : 0);
		}
		if (childrenUnderBenefit == 0) {
			return 0;
		}
		double totalBenefit = 0;
		double oldRegimeBenifit = benefitBefore95[childrenUnderBenefit > 10 ? 9 : childrenUnderBenefit - 1];
		for (BenefitChild child : children) {
			int age = child.getAge(calcDate);
			if (age < 6) {
				totalBenefit += benefit_0_5;
			} else if (age < 12) {
				totalBenefit += benefit_6_11;
			} else if (age < 16) { // has to be changed every year until 19
				totalBenefit += benefit_12_16;
			} else if (age < 19) {
				totalBenefit += oldRegimeBenifit;
			}
		}
		return (int) Math.floor(totalBenefit);
	}

	private static class SingletonHolder {
		public static final ChildBenefits INSTANCE = new ChildBenefits();
	}

	public static ChildBenefits getInstance() {
		return SingletonHolder.INSTANCE;
	}
}

// 0 t/m 5 jaar € 194.99
// 6 t/m 11 jaar € 236.77
// 12 t/m 16 jaar € 278.55

// Kindgebonden budget
// gezamelijk toets ink (jaaropgaaf dus na aftrek) - 28.897 = bijdrage inkomen
// 7,6% * bijdrage inkomen verminderen
// 1 kind € 1.011
// 2 kinderen € 1.466
// 3 kinderen € 1.826
// 4 kinderen € 2.110
// 5 kinderen € 2.299
// 6 kinderen of meer € 189 extra per kind
// 12-15 + 231
// 16-17 + 296

// Huurtoeslag
// max verzamelinkomen 29.350
// alleen max inkomen 21.625
// 65+ 20.325 en 27.750
// max huur 652.52 (onder 23 361.66) minimum 212.24
// max spaargeld 20.785
// max servicekosten 4 * 12 = 48 (gez electra, huismeester, onderhoud gez en gez
// schoonmaak)

// Uw situatie Vermogen per persoon
// U bent jonger dan 65
// € 20.785 + € 2.779 voor elk minderjarig kind

// U bent jonger dan 65 en een alleenstaande ouder. U hebt van 1 juli t/m 31
// december 2005 huursubsidie
// ontvangen bij een vermogen van meer dan € 20.300. In 2006, 2007, 2008, 2009
// en 2010 hebt u
// huurtoeslag ontvangen en in 2011 hebt u recht op alleenstaande-ouderkorting
// € 41.570

// U bent 65 jaar of ouder en uw inkomen is lager dan € 14.062
// € 48.301

// U bent 65 jaar of ouder en uw inkomen is € 14.062 tot en met € 19.562
// € 34.543

// U bent 65 jaar of ouder en uw inkomen is hoger dan € 19.562
// € 20.785
