package test;

import java.util.ArrayList;
import java.util.List;

public class Household {
	private List<Person> persons = new ArrayList<Person>();
	
	public void addPerson(Person person) {
		persons.add(person);
	}
	
	public int getTaxRelief() {
		return 0;
	}

}
