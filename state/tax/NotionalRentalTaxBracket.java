package nl.yasmijn.state.tax;


public class NotionalRentalTaxBracket extends TaxBracket {
	private double tariff;
	private int additionalAmount;

	public double getTariff() {
		return tariff;
	}

	public void setTariff(double tariff) {
		this.tariff = tariff;
	}

	public int getAdditionalAmount() {
		return additionalAmount;
	}

	public void setAdditionalAmount(int additionalAmount) {
		this.additionalAmount = additionalAmount;
	}
}
