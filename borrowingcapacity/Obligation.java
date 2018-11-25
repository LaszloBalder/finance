package nl.yasmijn.borrowingcapacity;

import java.math.BigDecimal;

public class Obligation {
	private BigDecimal amount;
	private ObligationType obligationType;
	private ObligationLifeInsurance lifeInsurance;

	public ObligationLifeInsurance getLifeInsurance() {
		return lifeInsurance;
	}

	public void setLifeInsurance(ObligationLifeInsurance lifeInsurance) {
		this.lifeInsurance = lifeInsurance;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public ObligationType getObligationType() {
		return obligationType;
	}

	public void setObligationType(ObligationType obligationType) {
		this.obligationType = obligationType;
	}
}
