package nl.yasmijn.riskpremium;

import java.util.ArrayList;
import java.util.List;

public class LifeTable {
	private static final int MaxAge = 150;
	private List<AgeRecord> lifeData = new ArrayList<AgeRecord>(MaxAge + 1);
	private List<AgeRecord> lifeDataFemale = new ArrayList<AgeRecord>(MaxAge + 1);

	private void addLifeData(List<AgeRecord> list, int age, double lx, double qx) throws IllegalArgumentException {
		if (age != list.size()) {
			throw new IllegalArgumentException("Records have to be added in order of age starting with 0 to " + MaxAge);
		} else {
			AgeRecord ar = new AgeRecord();
			ar.setAge(age);
			ar.setLx(lx);
			ar.setQx(qx);
			list.set(age, ar);
		}
	}

	public void addLifeData(int age, double lx, double qx) throws IllegalArgumentException {
		addLifeData('m', age, lx, qx);
	}

	public void addLifeData(char sex, int age, double lx, double qx) throws IllegalArgumentException {
		if (Character.toLowerCase(sex) == 'm' || Character.toLowerCase(sex) == 'f') {
			List<AgeRecord> list = Character.toLowerCase(sex) == 'm' ? lifeData : lifeDataFemale;
			addLifeData(list, age, lx, qx);
		} else {
			throw new IllegalArgumentException("Sex of type " + sex + " not supported");
		}
	}
}
