package nl.yasmijn.state.allowance;

public class CareAllowance {
	private static final int standardPremium = 1375;
	private static final int singleMaxIncome = 36022;
	private static final int coupleMaxIncome = 54264;
	private static final int thresholdIncome = 19890;
	private static final double singleIncomeFactor = 0.02715;
	private static final double coupleIncomeFactor = 0.05015;
	private static final double aboveThresholdIncomeFactor = 0.05030;

	private int calculateStandardPremium(int income, int personCount, boolean underCareLaw) {
		if (income > coupleMaxIncome || (personCount == 1 && income > singleMaxIncome)) {
			return 0;
		}
		double normativePremium = 0;

		if (personCount == 1) {
			normativePremium = singleIncomeFactor * thresholdIncome;
		} else if (personCount == 2) {
			normativePremium = coupleIncomeFactor * thresholdIncome;
		}
		if (income > thresholdIncome) {
			normativePremium += (income - thresholdIncome) * aboveThresholdIncomeFactor;
		}
		double allowance = personCount * standardPremium - normativePremium;
		if (!underCareLaw) {
			allowance /= 2;
		}
		return (int) Math.round(allowance);
	}

	public static void main(String[] args) {
		CareAllowance ca = new CareAllowance();
		System.out.println(ca.calculateStandardPremium(12000, 1, true));
		System.out.println(ca.calculateStandardPremium(19000, 2, true));
		System.out.println(ca.calculateStandardPremium(36040, 1, true));
		System.out.println(ca.calculateStandardPremium(54265, 2, true));
		System.out.println(ca.calculateStandardPremium(53000, 2, false));
	}
}
/*
 * België 0,6827 Bosnië-Herzegovina 0,0542 Bulgarije 0,0454 Cabo Verde 0,0323
 * Cyprus 0,1578 Denemarken 0,6590 Duitsland 0,7310 Estland 0,1474 Finland
 * 0,5326 Frankrijk 0,7747 Griekenland 0,3275 Groot Brittannië 0,7986 Hongarije
 * 0,1448 Ierland 1,0176 IJsland 1,0833 Italië 0,6058 Kroatië 0,1660 Letland
 * 0,0974 Liechtenstein 0,6938 Litouwen 0,1060 Luxemburg 0,8167 Macedonië 0,0448
 * Malta 0,2046 Marokko 0,0125 Montenegro 0,0605 Noorwegen 1,4227 Oostenrijk
 * 0,6596 Polen 0,0769 Portugal 0,3235 Roemenië 0,0437 Servië 0,0870 Slovenië
 * 0,2867 Slowakije 0,1542 Spanje 0,4073 Tjechië 0,2149 Tunesië 0,0209 Turkije
 * 0,0464 Zweden 0,8181 Zwitserland 0,5143
 */