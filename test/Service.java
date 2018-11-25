package test;

import java.util.Date;

public class Service {
	public BorrowCapacityOutput getMaxLoan(BorrowCapacityInput borrowCapacityParams) {
		MortgagePlan plan = new MortgagePlan(2010, null);
		for (PersonDto person : borrowCapacityParams.persons) {
			Person p = plan.createPerson(Sex.MALE, 19650114);
			p.addMonthlyIncome(50000, 201001, 201212);			
		}
		return null;
	}

}
