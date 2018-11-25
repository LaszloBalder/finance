package test;

import java.util.ArrayList;
import java.util.List;

import nl.yasmijn.finance.borrowingcapacity.DebtToIncomeRatio;
import nl.yasmijn.state.tax.TaxSystem;

public class Tester {
	public static void main(String[] args) {
		TaxSystem ts = getTaxSystem();
		DebtToIncomeRatio dtir = getDebtToIncomeRatio();
		MortgagePlan mp = new MortgagePlan(2010, ts);
		mp.addMonthlyIncome(2500, 201101, 203412);
		mp.addMonthlyIncome(1000, 201201, 203012);
		mp.addMonthlyIncome(100, 201507, 202006);
		mp.addMonthlyIncome(1500, 203501, 205012);
		mp.addMonthlyIncome(200, 203001, 205012);

		Obligation obl = new Obligation(201107, 204106, 1.0, PmtFreq.MONTHLY);
		obl.setAmount(AnnuityLibrary.calculatePayment(0.0435 / 12, 360, 190000));
		obl.setPrincipal(190000);
		obl.setRate(0.0435);
		mp.setObligation(obl);
		mp.setProprtyTaxValue(220000);
		List<IncomeTaxInfo> list = mp.getIncomeTaxInfo();
		// for (IncomeTaxInfo iti : list) {
		// System.out.printf("%10d %12.2f %12.2f %12d %12d\n", iti.getYear(),
		// iti.getIncome(), iti.getInterestPayed(), iti
		// .getTaxBenefit(), iti.getDownPayment());
		// }
		list = new ArrayList<IncomeTaxInfo>();
		long t0 = System.nanoTime();
		for (int i = 0; i < 0; i++) {
			mp.setProprtyTaxValue(220000 + i);
			list = mp.getIncomeTaxInfo();
		}
		long t1 = System.nanoTime();
		// for (IncomeTaxInfo iti : list) {
		// System.out.printf("%10d %12.2f %12.2f %12d %12d\n", iti.getYear(),
		// iti.getIncome(), iti.getInterestPayed(), iti
		// .getTaxBenefit(), iti.getDownPayment());
		// }
		System.out.println((t1 - t0) / 1000000.0);
		// System.out.println(AnnuityLibrary.calculatePresentValue(0.0435 / 12,
		// 360, 945.84));
		// System.out.println(Math.round(AnnuityLibrary.calculatePresentValue(0.0435
		// / 12, 360, 945.84) / 100) * 100);
		// System.out.println(AnnuityLibrary.calculateFutureValue(0.0435 / 12,
		// 360, 190000));
		double x = 0.0;
		t0 = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			x = 0.0;
			x = dtir.getDti(70, i, 0.058);
			// x = AnnuityLibrary.calculatePrincipal(0.0435/12, 190000, 360,
			// 120);
			// x = AnnuityLibrary.calcHighSpeedPrincipal(0.0435/12, 190000, 360,
			// 120);
		}
		t1 = System.nanoTime();
		System.out.println((t1 - t0) / 1000000.0);
		// System.out.println(ts.getDti(70, 50000, 0.058));

		int box3Amount = 20000;
		int hereditaryTenure = 0;
		double chfRrate = 0.052;
		int deductables;
		int inc1 = 50000;
		int inc2 = 0;
		int age1 = 46;
		int age2 = 40;

		double dtiRatio = dtir.getDti(age1, inc1, chfRrate) / 100;
		System.out.printf("dti ratio: %10.7f\n", dtiRatio);
		double allowedFinCost = (dtiRatio * (inc1+inc2));
		System.out.printf("Allowed basic financing cost: %10.2f\n", allowedFinCost);
		allowedFinCost -= hereditaryTenure;
		System.out.printf("Allowed financing cost after hereditary tenure: %10.2f\n", allowedFinCost);
		double allowedMonthCost = allowedFinCost / 12;
		System.out.printf("Allowed month cost: %10.2f\n", allowedMonthCost);
		System.out.printf("factor: %10.2f\n", getBox3Factor(dtiRatio, chfRrate));
		double box3Corr = getBox3part(chfRrate, box3Amount, dtiRatio);
		System.out.printf("Box3Correction: %10.2f\n", box3Corr);
		allowedMonthCost -= box3Corr;
		System.out.printf("Allowed month cost (box3 corrected): %10.2f\n", allowedMonthCost);
		int maxLoan = (int) AnnuityLibrary.calculatePresentValue(chfRrate / 12, 360, allowedMonthCost);
		System.out.printf("Max loan: %d\n", maxLoan + box3Amount);
		for (int i = 0; i < 1000; i++) {
			double xx = i / 300.0;
			System.out.println(xx);
		}
	}

	public static double getBox3part(double rate, int box3Amount, double dtiRatio) {
		return AnnuityLibrary.calculatePayment(rate / 12, 360, box3Amount) * getBox3Factor(dtiRatio, rate);
	}

	public static double getBox3Factor(double dtiRatio, double rate) {
		return (dtiRatio / (dtiRatio - (1.50 * rate + 0.01)));
	}

	public static DebtToIncomeRatio getDebtToIncomeRatio() {
		DebtToIncomeRatio dtir = new DebtToIncomeRatio();
		dtir.addChfDti(0, 20.30, 20.80, 21.30, 21.80, 22.30);
		dtir.addChfDti(18500, 20.30, 20.80, 21.30, 21.80, 22.30);
		dtir.addChfDti(19500, 23.00, 23.50, 24.00, 24.50, 25.00);
		dtir.addChfDti(20000, 24.00, 24.50, 25.00, 25.50, 26.00);
		dtir.addChfDti(20500, 25.00, 25.60, 26.20, 26.80, 27.40);
		dtir.addChfDti(21000, 25.60, 26.20, 26.80, 27.40, 28.00);
		dtir.addChfDti(21500, 26.40, 27.10, 27.80, 28.50, 29.20);
		dtir.addChfDti(22000, 27.10, 27.80, 28.50, 29.20, 29.90);
		dtir.addChfDti(22500, 27.80, 28.50, 29.20, 29.90, 30.60);
		dtir.addChfDti(23000, 28.40, 29.20, 30.00, 30.80, 31.60);
		dtir.addChfDti(23500, 29.00, 29.80, 30.60, 31.40, 32.20);
		dtir.addChfDti(24000, 29.40, 30.30, 31.20, 32.10, 33.00);
		dtir.addChfDti(38500, 29.50, 30.40, 31.30, 32.20, 33.10);
		dtir.addChfDti(39500, 29.60, 30.50, 31.40, 32.30, 33.20);
		dtir.addChfDti(40000, 29.70, 30.60, 31.50, 32.40, 33.30);
		dtir.addChfDti(41000, 29.80, 30.70, 31.60, 32.50, 33.40);
		dtir.addChfDti(41500, 29.90, 30.80, 31.70, 32.60, 33.50);
		dtir.addChfDti(42500, 30.00, 30.90, 31.80, 32.70, 33.60);
		dtir.addChfDti(43000, 30.10, 31.00, 31.90, 32.80, 33.70);
		dtir.addChfDti(43500, 30.20, 31.10, 32.00, 32.90, 33.80);
		dtir.addChfDti(44000, 30.30, 31.20, 32.10, 33.00, 33.90);
		dtir.addChfDti(44500, 30.50, 31.40, 32.30, 33.20, 34.10);
		dtir.addChfDti(45000, 30.60, 31.50, 32.40, 33.30, 34.20);
		dtir.addChfDti(45500, 30.70, 31.60, 32.50, 33.40, 34.30);
		dtir.addChfDti(46000, 30.80, 31.70, 32.60, 33.50, 34.40);
		dtir.addChfDti(46500, 30.90, 31.80, 32.70, 33.60, 34.50);
		dtir.addChfDti(47000, 31.00, 32.00, 33.00, 34.00, 35.00);
		dtir.addChfDti(47500, 31.10, 32.10, 33.10, 34.10, 35.10);
		dtir.addChfDti(48000, 31.20, 32.20, 33.20, 34.20, 35.20);
		dtir.addChfDti(48500, 31.30, 32.30, 33.30, 34.30, 35.30);
		dtir.addChfDti(49000, 31.40, 32.40, 33.40, 34.40, 35.40);
		dtir.addChfDti(49500, 31.50, 32.50, 33.50, 34.50, 35.50);
		dtir.addChfDti(50000, 31.60, 32.60, 33.60, 34.60, 35.60);
		dtir.addChfDti(50500, 31.70, 32.70, 33.70, 34.70, 35.70);
		dtir.addChfDti(51000, 31.80, 32.80, 33.80, 34.80, 35.80);
		dtir.addChfDti(51500, 31.90, 32.90, 33.90, 34.90, 35.90);
		dtir.addChfDti(52000, 32.00, 33.00, 34.00, 35.00, 36.00);
		dtir.addChfDti(52500, 32.00, 33.00, 34.00, 35.00, 36.00);
		dtir.addChfDti(53000, 32.10, 33.10, 34.10, 35.10, 36.10);
		dtir.addChfDti(53500, 32.20, 33.20, 34.20, 35.20, 36.20);
		dtir.addChfDti(54000, 32.30, 33.30, 34.30, 35.30, 36.30);
		dtir.addChfDti(54500, 32.40, 33.40, 34.40, 35.40, 36.40);
		dtir.addChfDti(55000, 32.50, 33.50, 34.50, 35.50, 36.50);
		dtir.addChfDti(55500, 32.70, 33.70, 34.70, 35.70, 36.70);
		dtir.addChfDti(56000, 32.80, 33.80, 34.80, 35.80, 36.80);
		dtir.addChfDti(56500, 32.90, 33.90, 34.90, 35.90, 36.90);
		dtir.addChfDti(57000, 33.00, 34.00, 35.00, 36.00, 37.00);
		dtir.addChfDti(57500, 33.10, 34.10, 35.10, 36.10, 37.10);
		dtir.addChfDti(58000, 33.20, 34.20, 35.20, 36.20, 37.20);
		dtir.addChfDti(60000, 33.70, 34.80, 35.90, 37.00, 38.10);
		dtir.addChfDti(62000, 34.10, 35.20, 36.30, 37.40, 38.50);
		dtir.addChfDti(64000, 34.50, 35.60, 36.70, 37.80, 38.90);
		dtir.addChfDti(66000, 34.80, 35.90, 37.00, 38.10, 39.20);
		dtir.addChfDti(68000, 35.20, 36.30, 37.40, 38.50, 39.60);
		dtir.addChfDti(70000, 35.70, 36.80, 38.00, 39.10, 40.20);
		dtir.addChfDti(72000, 36.10, 37.20, 38.30, 39.40, 40.50);
		dtir.addChfDti(74000, 36.30, 37.40, 38.50, 39.60, 40.70);
		dtir.addChfDti(76000, 36.40, 37.50, 38.60, 39.70, 40.80);
		dtir.addChfDti(78000, 36.60, 37.80, 39.00, 40.20, 41.40);
		dtir.addChfDti(80000, 36.70, 37.90, 39.10, 40.30, 41.50);
		dtir.addChfDti(82000, 36.80, 38.10, 39.40, 40.70, 42.00);
		dtir.addChfDti(86000, 36.90, 38.20, 39.50, 40.80, 42.10);
		dtir.addChfDti(90000, 37.00, 38.30, 39.60, 40.90, 42.20);
		dtir.addChfDti(110000, 37.00, 38.30, 39.60, 40.90, 42.20);
		dtir.addChfDti(Integer.MAX_VALUE, 37.00, 38.30, 39.60, 40.90, 42.20);

		dtir.addChfDti65(0, 19.50, 19.70, 19.90, 20.10, 20.30);
		dtir.addChfDti65(19000, 19.50, 19.70, 19.90, 20.10, 20.30);
		dtir.addChfDti65(19500, 21.00, 21.20, 21.40, 21.60, 21.80);
		dtir.addChfDti65(20000, 22.50, 22.70, 22.90, 23.10, 23.30);
		dtir.addChfDti65(20500, 23.90, 24.10, 24.30, 24.50, 24.70);
		dtir.addChfDti65(21000, 25.20, 25.40, 25.60, 25.80, 26.00);
		dtir.addChfDti65(21500, 26.10, 26.30, 26.50, 26.70, 26.90);
		dtir.addChfDti65(22000, 26.60, 26.80, 27.00, 27.20, 27.40);
		dtir.addChfDti65(22500, 27.10, 27.30, 27.50, 27.70, 27.90);
		dtir.addChfDti65(23000, 27.60, 27.80, 28.00, 28.20, 28.40);
		dtir.addChfDti65(23500, 28.10, 28.30, 28.50, 28.70, 28.90);
		dtir.addChfDti65(24000, 28.50, 28.80, 29.10, 29.40, 29.70);
		dtir.addChfDti65(24500, 28.90, 29.20, 29.50, 29.80, 30.10);
		dtir.addChfDti65(25000, 29.00, 29.30, 29.60, 29.90, 30.20);
		dtir.addChfDti65(25500, 29.20, 29.60, 30.00, 30.40, 30.80);
		dtir.addChfDti65(26000, 29.30, 29.70, 30.10, 30.50, 30.90);
		dtir.addChfDti65(26500, 29.50, 30.00, 30.50, 31.00, 31.50);
		dtir.addChfDti65(27000, 29.60, 30.10, 30.60, 31.10, 31.60);
		dtir.addChfDti65(27500, 29.70, 30.20, 30.70, 31.20, 31.70);
		dtir.addChfDti65(28000, 29.90, 30.40, 30.90, 31.40, 31.90);
		dtir.addChfDti65(28500, 30.10, 30.60, 31.10, 31.60, 32.10);
		dtir.addChfDti65(29000, 30.30, 30.80, 31.30, 31.80, 32.30);
		dtir.addChfDti65(29500, 30.50, 31.00, 31.50, 32.00, 32.50);
		dtir.addChfDti65(30000, 30.70, 31.20, 31.70, 32.20, 32.70);
		dtir.addChfDti65(30500, 31.00, 31.50, 32.00, 32.50, 33.00);
		dtir.addChfDti65(31000, 31.20, 31.70, 32.20, 32.70, 33.20);
		dtir.addChfDti65(31500, 31.40, 31.90, 32.40, 32.90, 33.40);
		dtir.addChfDti65(32000, 31.70, 32.20, 32.70, 33.20, 33.70);
		dtir.addChfDti65(32500, 31.90, 32.40, 32.90, 33.40, 33.90);
		dtir.addChfDti65(33000, 32.10, 32.60, 33.10, 33.60, 34.10);
		dtir.addChfDti65(33500, 32.30, 32.80, 33.30, 33.80, 34.30);
		dtir.addChfDti65(34000, 32.50, 33.00, 33.50, 34.00, 34.50);
		dtir.addChfDti65(34500, 32.70, 33.20, 33.70, 34.20, 34.70);
		dtir.addChfDti65(35000, 33.00, 33.50, 34.00, 34.50, 35.00);
		dtir.addChfDti65(35500, 33.30, 33.80, 34.30, 34.80, 35.30);
		dtir.addChfDti65(36000, 33.50, 34.00, 34.50, 35.00, 35.50);
		dtir.addChfDti65(36500, 33.70, 34.30, 34.90, 35.50, 36.10);
		dtir.addChfDti65(37000, 33.90, 34.50, 35.10, 35.70, 36.30);
		dtir.addChfDti65(37500, 34.10, 34.70, 35.30, 35.90, 36.50);
		dtir.addChfDti65(38000, 34.20, 34.90, 35.60, 36.30, 37.00);
		dtir.addChfDti65(38500, 34.40, 35.10, 35.80, 36.50, 37.20);
		dtir.addChfDti65(39000, 34.60, 35.30, 36.00, 36.70, 37.40);
		dtir.addChfDti65(39500, 34.80, 35.50, 36.20, 36.90, 37.60);
		dtir.addChfDti65(40000, 34.90, 35.60, 36.30, 37.00, 37.70);
		dtir.addChfDti65(40500, 35.10, 35.80, 36.50, 37.20, 37.90);
		dtir.addChfDti65(41000, 35.20, 35.90, 36.60, 37.30, 38.00);
		dtir.addChfDti65(41500, 35.40, 36.10, 36.80, 37.50, 38.20);
		dtir.addChfDti65(42000, 35.50, 36.20, 36.90, 37.60, 38.30);
		dtir.addChfDti65(42500, 35.70, 36.40, 37.10, 37.80, 38.50);
		dtir.addChfDti65(43000, 36.00, 36.70, 37.40, 38.10, 38.80);
		dtir.addChfDti65(43500, 36.20, 36.90, 37.60, 38.30, 39.00);
		dtir.addChfDti65(44000, 36.40, 37.10, 37.80, 38.50, 39.20);
		dtir.addChfDti65(44500, 37.10, 37.90, 38.70, 39.50, 40.30);
		dtir.addChfDti65(45000, 37.30, 38.10, 38.90, 39.70, 40.50);
		dtir.addChfDti65(45500, 37.50, 38.30, 39.10, 39.90, 40.70);
		dtir.addChfDti65(46000, 37.80, 38.60, 39.40, 40.20, 41.00);
		dtir.addChfDti65(46500, 38.00, 38.80, 39.60, 40.40, 41.20);
		dtir.addChfDti65(47000, 38.20, 39.00, 39.80, 40.60, 41.40);
		dtir.addChfDti65(47500, 38.40, 39.20, 40.00, 40.80, 41.60);
		dtir.addChfDti65(48000, 38.70, 39.50, 40.30, 41.10, 41.90);
		dtir.addChfDti65(48500, 38.90, 39.70, 40.50, 41.30, 42.10);
		dtir.addChfDti65(49000, 39.10, 39.90, 40.70, 41.50, 42.30);
		dtir.addChfDti65(49500, 39.30, 40.10, 40.90, 41.70, 42.50);
		dtir.addChfDti65(50000, 39.50, 40.30, 41.10, 41.90, 42.70);
		dtir.addChfDti65(50500, 39.70, 40.50, 41.30, 42.10, 42.90);
		dtir.addChfDti65(51000, 39.90, 40.70, 41.50, 42.30, 43.10);
		dtir.addChfDti65(51500, 40.00, 40.80, 41.60, 42.40, 43.20);
		dtir.addChfDti65(52000, 40.20, 41.00, 41.80, 42.60, 43.40);
		dtir.addChfDti65(52500, 40.40, 41.20, 42.00, 42.80, 43.60);
		dtir.addChfDti65(53000, 40.50, 41.30, 42.10, 42.90, 43.70);
		dtir.addChfDti65(53500, 40.70, 41.50, 42.30, 43.10, 43.90);
		dtir.addChfDti65(54000, 40.90, 41.70, 42.50, 43.30, 44.10);
		dtir.addChfDti65(54500, 41.00, 41.80, 42.60, 43.40, 44.20);
		dtir.addChfDti65(55000, 41.20, 42.00, 42.80, 43.60, 44.40);
		dtir.addChfDti65(55500, 41.30, 42.10, 42.90, 43.70, 44.50);
		dtir.addChfDti65(56000, 41.40, 42.20, 43.00, 43.80, 44.60);
		dtir.addChfDti65(56500, 41.60, 42.50, 43.40, 44.30, 45.20);
		dtir.addChfDti65(57000, 41.70, 42.60, 43.50, 44.40, 45.30);
		dtir.addChfDti65(57500, 41.90, 42.80, 43.70, 44.60, 45.50);
		dtir.addChfDti65(58000, 42.00, 42.90, 43.80, 44.70, 45.60);
		dtir.addChfDti65(60000, 42.40, 43.30, 44.20, 45.10, 46.00);
		dtir.addChfDti65(62000, 42.40, 43.40, 44.40, 45.40, 46.40);
		dtir.addChfDti65(64000, 42.40, 43.60, 44.80, 46.00, 47.20);
		dtir.addChfDti65(66000, 42.40, 43.70, 45.00, 46.30, 47.60);
		dtir.addChfDti65(68000, 42.40, 43.80, 45.20, 46.60, 48.00);
		dtir.addChfDti65(78000, 42.50, 43.90, 45.30, 46.70, 48.10);
		dtir.addChfDti65(84000, 42.60, 44.00, 45.40, 46.80, 48.20);
		dtir.addChfDti65(96000, 42.70, 44.10, 45.50, 46.90, 48.30);
		dtir.addChfDti65(98000, 42.80, 44.20, 45.60, 47.00, 48.40);
		dtir.addChfDti65(100000, 42.90, 44.30, 45.70, 47.10, 48.50);
		dtir.addChfDti65(102000, 43.00, 44.40, 45.80, 47.20, 48.60);
		dtir.addChfDti65(104000, 43.10, 44.60, 46.10, 47.60, 49.10);
		dtir.addChfDti65(106000, 43.20, 44.70, 46.20, 47.70, 49.20);
		dtir.addChfDti65(108000, 43.30, 44.80, 46.30, 47.80, 49.30);
		dtir.addChfDti65(110000, 43.40, 44.90, 46.40, 47.90, 49.40);
		dtir.addChfDti65(Integer.MAX_VALUE, 43.40, 44.90, 46.40, 47.90, 49.40);
		return dtir;

	}
	public static TaxSystem getTaxSystem() {
		TaxSystem ts = new TaxSystem();
		
		ts.addNotionalRentalTaxTariff(0, 12500, 0.00);
		ts.addNotionalRentalTaxTariff(12501, 25000, 0.002);
		ts.addNotionalRentalTaxTariff(25001, 50000, 0.003);
		ts.addNotionalRentalTaxTariff(50001, 75000, 0.004);
		ts.addNotionalRentalTaxTariff(75001, 1020000, 0.0055);
		ts.addNotionalRentalTaxTariff(1020001, Integer.MAX_VALUE, 0.0105, 5610);

		ts.addTaxBracket(0, 18628, 0.33, 0.151);
		ts.addTaxBracket(18628, 33436, 0.4195, 0.2405);
		ts.addTaxBracket(33436, 55694, 0.42, 0.42);
		ts.addTaxBracket(55694, Integer.MAX_VALUE, 0.52, 0.52);
		
		return ts;
	}
}
