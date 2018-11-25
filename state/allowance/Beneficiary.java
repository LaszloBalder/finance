package nl.yasmijn.state.allowance;

import java.util.Date;

public interface Beneficiary {
	int getIncome();
	int getAge(Date calcDate);
	double getDisabilityRate();
	boolean isUnderCareLaw();
}
