package nl.yasmijn.utils;

public class QuickDate {
	private int date;

	public QuickDate(int date) {
		if (!DateMath.isValidDate(date)) {
			throw new IllegalArgumentException();
		}
		this.date = date;
	}

	private QuickDate(int date, boolean checked) {
		this.date = date;
	}

	public int getDate() {
		return date;
	}

	public static QuickDate getToday() {
		return new QuickDate(DateMath.getToday(), true);
	}

	public QuickDate addMonth(int months) {
		int tempMonth = ((date / 100) % 100) + months - 1; // Let's make January = 0, February = 1 etc
		int year = (date / 10000) + tempMonth / 12; 
		int newMonth = tempMonth % 12;
		if (newMonth < 0) {
			newMonth = 12 + newMonth;
			year--;
		}
		int newDate = (year * 10000) + ((newMonth + 1) * 100) + (date % 100);
		while (!DateMath.isValidDate(newDate)) {
			newDate--;
		}
		return new QuickDate(newDate, true);
	}

	public static void main(String[] args) {
		System.out.println(new QuickDate(20040229).addMonth(-12).getDate());
		System.out.println(new QuickDate(20040229).addMonth(12).getDate());
	}
}
