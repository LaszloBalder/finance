package nl.yasmijn.borrowingcapacity;

public class Name {
	private String nickName;
	private String firstName;
	private String middleNames;
	private String initials;
	private String lastName;
	private String lastNamePrefix;
	private String familyName;
	private String familyNamePrefix;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastNamePrefix() {
		return lastNamePrefix;
	}

	public void setLastNamePrefix(String lastNamePrefix) {
		this.lastNamePrefix = lastNamePrefix;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFamilyNamePrefix() {
		return familyNamePrefix;
	}

	public void setFamilyNamePrefix(String familyNamePrefix) {
		this.familyNamePrefix = familyNamePrefix;
	}

	public String getLegalName() {
		return 
		firstName
		+ (middleNames != null ? (" " + middleNames) : "")
		+ (familyNamePrefix != null ? (" " + familyNamePrefix) : "")
		+ (familyName != null ? (" " + familyName) : "")
		;
	}
	
	public String getInitialedName() {
		return 
		initials
		+ (familyNamePrefix != null ? (" " + familyNamePrefix) : "")
		+ (familyName != null ? (" " + familyName + " - ") : "")
		+ (lastNamePrefix != null ? (" " + lastNamePrefix) : "")
		+ (lastName != null ? (" " + lastName) : "")
		;
	}
}
