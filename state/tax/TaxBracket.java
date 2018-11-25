package nl.yasmijn.state.tax;

public class TaxBracket {
	private int bracketOrder;
	private int lowerBound;
	private int upperBound;

	public int getBracketOrder() {
		return bracketOrder;
	}

	public void setBracketOrder(int bracketOrder) {
		this.bracketOrder = bracketOrder;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}
}
