package nl.yasmijn.utils;

public class RangeKey implements Comparable<RangeKey> {
	private int lower;
	private int upper;
	private int value;
	private boolean isRange = true;

	public RangeKey(int lower, int upper) {
		if (upper <= lower) {
			throw new IllegalArgumentException();
		}
		this.lower = lower;
		this.upper = upper;
	}

	public RangeKey(int value) {
		this.value = value;
		this.isRange = false;
	}

	@Override
	public int compareTo(RangeKey rangeKey) {
		if (this.isRange) {
			if (rangeKey.isRange) {
				if (rangeKey.upper < this.lower)
					return -1;
				if (rangeKey.lower > this.upper)
					return 1;
				throw new IllegalArgumentException();
			} else {
				if (this.upper < rangeKey.value)
					return 1;
				if (this.lower > rangeKey.value)
					return -1;
				return 0;
			}
		} else { // !this.isRange
			if (rangeKey.isRange) {
				if (this.value < rangeKey.lower)
					return 1;
				if (this.value > rangeKey.upper)
					return -1;
				return 0;
			} else {
				return rangeKey.value - this.value;
			}
		}
	}
}
