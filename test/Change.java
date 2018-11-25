package test;

public class Change implements Comparable<Change> {
	public int period;
	public double amount;

	@Override
	public int compareTo(Change o) {
		return this.period - o.period;
	}
}
