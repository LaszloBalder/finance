package nl.yasmijn.state.tax;


public class IncomeTaxBracket extends TaxBracket {
	private double taxRate;
	private double specialTaxRate;

	public double getTaxRate(boolean special) {
		if (special) {
			return specialTaxRate;
		} else {
			return taxRate;
		}
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getSpecialTaxRate() {
		return specialTaxRate;
	}

	public void setSpecialTaxRate(double specialTaxRate) {
		this.specialTaxRate = specialTaxRate;
	}
}
