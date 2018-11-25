package test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowCapacityInput {
	public BigInteger TaxPropertyValue;
	public BigInteger HereditaryTenure;
	public BigInteger LoanDurationInYears;
	public BigInteger BoxThreeAmount;
	public BigInteger NonMortgageFinancingCost;
	public Date CaclulationDate;
	public BigDecimal InterestRate;
	public List<PersonDto> persons = new ArrayList<PersonDto>(); 
}
