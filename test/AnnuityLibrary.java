package test;

import nl.yasmijn.utils.StopWatch;

public final class AnnuityLibrary {
	private final static double ONE = 1.0;
	private final static int safetyLimit = 20;

	/*
	 * Calculates the payment for a loan with fixed interest and constant
	 * payments
	 */
	public final static double calculatePayment(double rate, int periods, int principal) {
		return (rate * principal) / (ONE - Math.pow(ONE + rate, -periods));
	}

	/*
	 * Calculate the principal at period for a loan with fixed interest and
	 * constant payments
	 */
	public final static double calculatePrincipal(double rate, int principal, int periods, int period) {
		double payment = calculatePayment(rate, periods, principal);
		return principal * annFact(rate, period) - (payment / rate) * (annFact(rate, period) - ONE);
	}

	public final static double calcHighSpeedPrincipal(double rate, int principal, int periods, int period) {
		return principal * annFact(rate, period) - (calculatePayment(rate, periods, principal) / rate)
				* (annFact(rate, period) - ONE);
	}

	/*
	 * Present value of a series of payments with a fixed rate (PV in Excel)
	 */
	public final static double calculatePresentValue(double rate, int periods, double payment) {
		return payment * (ONE - Math.pow(ONE + rate, -periods)) / rate;
	}

	/*
	 * Future value of an amount in a series of payments with a fixed rate (FV
	 * in Excel)
	 */
	public final static double calculateFutureValue(double rate, int periods, double payment) {
		return payment * (Math.pow(ONE + rate, periods) - ONE) / rate;
	}

	/*
	 * Future value of an amount in a series of payments with a fixed rate, with
	 * initial deposit (FV in Excel)
	 */
	public final static double calculateFutureValue(double periodRate, int periods, double payment, double initialAmount) {
		double growFactor = Math.pow(ONE + periodRate, periods);
		return (initialAmount * growFactor) + ((growFactor - ONE) / periodRate * payment);
	}

	// TODO: future value with due end or start of month

	/*
	 * Annuity factor based on rate and periods
	 */
	public final static double calculateAnnuityFactor(double rate, int periods) {
		return (1 - Math.pow(ONE + rate, -periods)) / rate;
	}

	private final static double annFact(double rate, int periods) {
		return Math.pow(ONE + rate, periods);
	}

	/*
	 * Calculate the high/low saving with known switch period. future value is
	 * first calculated with high payment = 10 * annuity payment because payment
	 * is linear with the future value, payment can be multiplied with target
	 * amount / calculated future value
	 */
	private static HighLowSaving calculateHighLowSavings(double rate, int periods, int amount, int highEnd) {
		double annuity = amount / ((Math.pow(ONE + rate, periods) - ONE) / rate);
		double lowPayment = annuity;
		double futureValue = calculateHighLowFutureValue(rate, periods, highEnd, lowPayment);
		lowPayment = amount / futureValue * lowPayment;
		futureValue = calculateHighLowFutureValue(rate, periods, highEnd, lowPayment);
		return new HighLowSaving(lowPayment, periods, highEnd, futureValue);
	}

	private static double calculateHighLowFutureValue(double rate, int periods, int highEnd, double lowPayment) {
		double endHighAmount = calculateFutureValue(rate, highEnd, 10 * lowPayment);
		return calculateFutureValue(rate, periods - highEnd, lowPayment, endHighAmount);
	}

	private static HighLowSaving calculateOptimzedHighLow(double rate, int years, int periodsInYear, int target) {
		double periodRate = getNominalPeriodRate(rate, periodsInYear);
		int periods = periodsInYear * years;
		int offset = periods / 3;
		int jump = offset / 2;
		double left = 0, right = 0;
		HighLowSaving middle = null;
		while (jump > 0) {
			left = calculateHighLowSavings(periodRate, periods, target, offset - 1).totalPaid;
			middle = calculateHighLowSavings(periodRate, periods, target, offset);
			right = calculateHighLowSavings(periodRate, periods, target, offset + 1).totalPaid;
			if (middle.totalPaid < left && middle.totalPaid < right + 0.001) {
				jump = 0;
			} else {
				if (right > middle.totalPaid) {
					offset -= jump;
				} else {
					offset += jump;
				}
			}
			if (jump > 1) {
				jump /= 2;
			}
		}
		return middle;
	}

	public static double determineRate(int initialCapital, int targetCapital, double payment, int periods) {
		// System.out.println("determineRate");
		// if (pv != 0) {
		// i = (-fv - pv - pmt * n) / pv / n;
		// } else {
		// i = -fv / pmt / n / n;
		// }

		double rate = .1;
		// if (initialCapital != 0) {
		// rate = (-targetCapital + initialCapital + payment) / initialCapital /
		// periods;
		// }
		// else {
		// rate = -targetCapital / payment / periods / periods;
		// }
		// System.out.println("rate: " +rate);
		double futureValue = calculateFutureValue(rate, periods, payment, initialCapital);
		double error;
		int safetyCounter = 0;
		while (Math.abs(targetCapital - futureValue) > 0.5 && safetyCounter++ < safetyLimit) {
			error = (futureValue - targetCapital) / futureValue;
			rate /= 1 - error;
			futureValue = calculateFutureValue(rate, periods, payment, initialCapital);
		}
		if (safetyCounter > safetyLimit) {
			rate = 0;
		}
		return rate;
	}

	public final static double getNominalPeriodRate(double effectiveRate, int periodsInYear) {
		return Math.pow(ONE + effectiveRate, ONE / periodsInYear) - ONE;
	}

	public final static double getEffectiveRate(double effectiveRate, int periodsInYear) {
		return Math.pow(ONE + effectiveRate / periodsInYear, periodsInYear) - ONE;
	}

	public static double ln1(double x) {
		if (Math.abs(x) >= 0.01)
			return Math.log(1 + x);

		double c = 2;
		double s = x;
		double t = x;
		double s1;

		x = -x;
		for (;;) {
			t *= x;
			s1 = s - t / c;
			if (s1 == s)
				break;
			c += 1;
			s = s1;
		}
		// System.out.println(c);
		return s1;
	}

	private final static double tvmi(double pv, double pmt, double fv, int n) {
		double i;
		double i1;

		/* use guess for i */
		if (pv != 0) {
			i = (-fv - pv - pmt * n) / pv / n;
		} else {
			i = -fv / pmt / n / n;
		}

		/* iterate to find i given the input values */
		for (;;) {
			double t1 = Math.exp(n * ln1(i));
			double t2 = pmt / i * (t1 - 1);
			double t3 = t2 + fv + pv * t1;
			double t4 = (t2 + (fv * i - pmt) * n / (1 + i)) / i;
			i1 = i + t3 / t4;
			if (Math.abs((i1 - i) / i1) < 1e-15)
				break;
			i = i1;
		}
		return i1;
	}

	private final static double newFV(double i, int n, double pmt, int pv) {
		double il = Math.exp(n * ln1(i));
		double fv = -pv * il - (pmt * (il - 1) / i);
		return fv;
	}

	private final static double newPV(double i, int n, double pmt, int fv) {
		double il = Math.exp(n * ln1(i));
		double pv = (-fv - (pmt * (il - 1) / i)) / il;
		return pv;
	}

	private static double newPmt(double i, int n, double pv, double fv) {
		double il = Math.exp(n * ln1(i));
		double pmt = ((pv*il +fv ) * i) / (1 - il);
		return pmt;
	}
	
	public static void main(String[] args) {
		System.out.println(newPmt(.05/12, 360, -18661.54, 0));
		System.out.println(tvmi(-1000, -100, 6000, 24));
		System.out.println(tvmi(590, -222.16, 590, 32));
		System.out.println(newFV(0.05, 2, -2400, -1000));
		int n = 365 * 24 * 3600;
		System.out.println(newFV(0.1 / n, n, -0.01, 0));
		System.out.println(newPV(0.05 / 12, 360, -100, 0));
		System.out.println(calculatePresentValue(0.05 / 12, 360, 100));
		// double tot = 0;
		// StopWatch sw = new StopWatch();
		// sw.start();
		// for (int i = 0; i < 1000000; i++) {
		// tot = newFV(0.01, -0.01, 365, 0);
		// }
		// sw.stop();
		// System.out.println(sw.getTimeInMillis());
		// System.out.println(tot);
		// tot = 0;
		// sw = new StopWatch();
		// sw.start();
		// for (int i = 0; i < 1000000; i++) {
		// tot = calculateFutureValue(0.01, 365, .01, 0);
		// }
		// sw.stop();
		// System.out.println(sw.getTimeInMillis());
		// System.out.println(tot);

		//System.out.println(ln1(.1 / 31536000));
		// System.out.println(calculateOptimzedHighLow(0.03, 15, 12,
		// 100000).totalPaid);
		// System.out.println(calculateOptimzedHighLow(0.03, 15, 12,
		// 100000).lowPayment);
		// System.out.println(calculateOptimzedHighLow(0.03, 15, 12,
		// 100000).highEnd);
		// System.out.println(calculateOptimzedHighLow(0.03, 15, 12,
		// 100000).result);
		//System.out.println(determineRate(1000, 6000, 100, 24));
		//System.out.println(determineRate(590, 0, 222.16, 32));
		// System.out.println(calculateRate(1000, 6000, 2400, 2));
		// System.out.println(String.format("%12.2f", calculateFutureValue(.05 /
		// 12, 60, 1000, 2400)));
		// System.out.println(getNominalPeriodRate(.03, 12));
		// System.out.println(getEffectiveRate(.06, 12));
		// int amount = 169646;
		// int years = 30;
		// double rate = 0.08;
		// HighLowSaving prev2 = calculateHighLowSavings(Math.pow(ONE + rate,
		// ONE / 12) - ONE, 12 * years, amount, 37);
		// System.out.println("prev2");
		// System.out.println(prev2.lowPayment);

		// HighLowSaving prev = calculateHighLowSavings(rate / 12, 12 * years,
		// amount, 1);
		// HighLowSaving current;
		//
		// StopWatch sw = new StopWatch();
		// sw.start();
		// // for (int j = 0; j < 1000; j++) {
		// for (int i = 2; i < 2; i++) {
		// current = calculateHighLowSavings(rate / 12, 12 * years, amount, i);
		// if (current.totalPayed < prev.totalPayed) {
		// prev = current;
		// continue;
		// }
		// System.out.println(String.format("%d %f %f %f %f", prev.highEnd,
		// prev.lowPayment, prev.totalPayed,
		// prev.annuity, prev.result));
		// break;
		// }
		// sw.stop();
		// System.out.println(sw.getTimeInMillis());
		// sw.start();
		// int offset = 0;
		// for (int i = 0; i < 21; i++)
		// offset = calculateOptimzedHighLow(rate, years, 12, amount).highEnd;
		// sw.stop();
		// System.out.println(sw.getTimeInMillis());
		// System.out.println(offset);
		//
		// double tot = 0;
		// StopWatch sw = new StopWatch();
		// sw.start();
		// for (int i = 0; i < 100000; i++) {
		// tot += determineRate(50000, 150000, 2400, 20);
		// }
		// System.out.println(tot / 100000);
		//
		// sw.stop();
		// System.out.println(sw.getTimeInMillis());

	}
}

class HighLowSaving {
	public int highEnd;
	public double lowPayment;
	public double totalPaid;
	public double result;

	public HighLowSaving(double lowPayment, int periods, int highEnd, double result) {
		this.highEnd = highEnd;
		this.totalPaid = (highEnd * 10 * lowPayment) + ((periods - highEnd) * lowPayment);
		this.lowPayment = lowPayment;
		this.result = result;
	}
}
