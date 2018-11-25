package nl.yasmijn.borrowingcapacity;

import java.util.Set;
import java.util.TreeSet;

public class Country {
	private String name;
	private String iso3166Code;
	private String nationality;
	private Set<StateUnion> memberOf = new TreeSet<StateUnion>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIso3166Code() {
		return iso3166Code;
	}

	public void setIso3166Code(String iso3166Code) {
		this.iso3166Code = iso3166Code;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public boolean isMemberOf(StateUnion union) {
		return memberOf.contains(union);
	}

	public void saddMembership(StateUnion union) {
		this.memberOf.add(union);
	}
}
