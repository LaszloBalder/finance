package test;

public class Obligation extends PeriodicPayment {
	private int principal = 0;
	private int obligationType = 0;
	private double rate = 0.0435;

	public Obligation(int startPeriod, int endPeriod, double amount, PmtFreq pmtFreq) {
		super(startPeriod, endPeriod, amount, pmtFreq);
	}

	public int getPrincipal() {
		return principal;
	}

	public void setPrincipal(int principal) {
		this.principal = principal;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getObligationType() {
		return obligationType;
	}

	public void setObligationType(int obligationType) {
		this.obligationType = obligationType;
	}

	public double getInterestPayed(int year) {
		int start = year * 100 + 1;
		int end = year * 100 + getPmtFreq().getFraction();
		return getAmount(start, end) - getDownPaymentInYear(year);
	}

	public double getPrincipalAtYearEnd(int year) {
		int end = year * 100 + getPmtFreq().getFraction();
		if (end < getStartPeriod() || end >= getEndPeriod()) {
			return 0.0;
		}
		int endPaymentNo = getPeriodsBetween(getStartPeriod(), end, getPmtFreq());
		double endPrincipal = AnnuityLibrary.calculatePrincipal(rate / getPmtFreq().getFraction(),principal , getDuration(),
				 endPaymentNo);
		return endPrincipal;
	}

	public double getDownPaymentInYear(int year) {
		int start = year * 100 + 1;
		int end = year * 100 + getPmtFreq().getFraction();
		if (start >= getEndPeriod() || end < getStartPeriod()) {
			return 0;
		}
		start = start < getStartPeriod() ? getStartPeriod() : start;
		end = end > getEndPeriod() ? getEndPeriod() : end;
		
		int startPaymentNo = getPeriodsBetween(getStartPeriod(), start, getPmtFreq()) - 1;
		int endPaymentNo = getPeriodsBetween(getStartPeriod(), end, getPmtFreq());
		double endPrincipal = AnnuityLibrary.calculatePrincipal(rate / getPmtFreq().getFraction(), principal, getDuration(), endPaymentNo);
		double startPrincipal = AnnuityLibrary.calculatePrincipal(rate / getPmtFreq().getFraction(), principal, getDuration(),startPaymentNo);
		return Math.round((startPrincipal - endPrincipal) * 100) / 100.0;
	}
}
