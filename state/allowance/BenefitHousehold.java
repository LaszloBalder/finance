package nl.yasmijn.state.allowance;

import java.util.List;

public interface BenefitHousehold {
	boolean hasDisablityHomeAdjustments();
	List<Beneficiary> getBeneficaries();
	List<BenefitChild> getChildren();
	String getCountry();
}
