package nl.yasmijn.borrowingcapacity;

import java.util.HashSet;
import java.util.Set;

public class ObligationLifeInsurance {
	private Set<BenefitChild> persons = new HashSet<BenefitChild>();
	int personCount = 0;

	public ObligationLifeInsurance(Obligation obligation, BenefitChild person) {
		this.persons.add(person);
	}
	
	public void addPerson(BenefitChild person) {
		this.persons.add(person);		
	}

	public BenefitChild[] getPersons() {
		BenefitChild [] p = new BenefitChild[persons.size()];
		return persons.toArray(p);
	}
}
