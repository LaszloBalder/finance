package nl.yasmijn.utils;

public class Period {
	private int years = 0;
	private int month = 0;
	private int days = 0;
	private int hours = 0;
	private int minutes = 0;
	private double seconds = 0;
	private static final String periodPrefix = "P";
	private static final char timePrefix = 'T';
	private PeriodDirection direction = PeriodDirection.Undefined;

	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append(direction == PeriodDirection.Negative ? "-P" : "P");
		return buff.toString();
	}

	private void setDirection(int periodPart) {
		if (periodPart == 0) {
			return;
		} else if (periodPart > 0 && direction != PeriodDirection.Negative) {
			direction = PeriodDirection.Positive;
			return;
		} else if (periodPart < 0 && direction != PeriodDirection.Positive) {
			direction = PeriodDirection.Negative;
			return;
		} else {
			throw new IllegalArgumentException("Period is completly positive or completly negative");
		}
		
	}

}

enum PeriodDirection {
	Positive, Negative, Undefined
}