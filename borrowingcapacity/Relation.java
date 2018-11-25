package nl.yasmijn.borrowingcapacity;

import java.math.BigDecimal;

public class Relation {
	private int startDate;
	private int endDate;
	private int numberOfChildren;
	private int youngestDateOfBirth;
	private BigDecimal pensionRights;
	private BigDecimal pensionObligations;

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public int getYoungestDateOfBirth() {
		return youngestDateOfBirth;
	}

	public void setYoungestDateOfBirth(int youngestDateOfBirth) {
		this.youngestDateOfBirth = youngestDateOfBirth;
	}

	public BigDecimal getPensionRights() {
		return pensionRights;
	}

	public void setPensionRights(BigDecimal pensionRights) {
		this.pensionRights = pensionRights;
	}

	public BigDecimal getPensionObligations() {
		return pensionObligations;
	}

	public void setPensionObligations(BigDecimal pensionObligations) {
		this.pensionObligations = pensionObligations;
	}
}
