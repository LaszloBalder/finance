package nl.yasmijn.borrowingcapacity;

public class HouseholdTypeKey implements Comparable<HouseholdTypeKey> {
	private boolean multiple;
	private boolean pensioner;

	public HouseholdTypeKey(boolean multiple, boolean pensioner) {
		this.multiple = multiple;
		this.pensioner = pensioner;
	}

	@Override
	public int compareTo(HouseholdTypeKey rhs) {
		if (this.multiple == rhs.multiple) {
			return new Boolean(this.pensioner).compareTo(rhs.pensioner);
		} else {
			return new Boolean(this.multiple).compareTo(rhs.multiple);
		}
	}
	
	public static void main(String[] args) {
		HouseholdTypeKey k1 = new HouseholdTypeKey(true, true);
		HouseholdTypeKey k2 = new HouseholdTypeKey(true, false);
		HouseholdTypeKey k3 = new HouseholdTypeKey(false, true);
		HouseholdTypeKey k4 = new HouseholdTypeKey(false, false);
		System.out.println(k1.compareTo(k1));
		System.out.println(k1.compareTo(k2));
		System.out.println(k1.compareTo(k3));
		System.out.println(k1.compareTo(k4));
		System.out.println(k2.compareTo(k1));
		System.out.println(k2.compareTo(k2));
		System.out.println(k2.compareTo(k3));
		System.out.println(k2.compareTo(k4));
		System.out.println(k3.compareTo(k1));
		System.out.println(k3.compareTo(k2));
		System.out.println(k3.compareTo(k3));
		System.out.println(k3.compareTo(k4));
		System.out.println(k4.compareTo(k1));
		System.out.println(k4.compareTo(k2));
		System.out.println(k4.compareTo(k3));
		System.out.println(k4.compareTo(k4));
	}
}
