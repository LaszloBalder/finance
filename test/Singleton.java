package test;

public class Singleton {
	private static Singleton instance = null;
	public Singleton(Singleton data)
	{
		instance = data;
	}

	protected Singleton() {
		
	}

	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
	
	public double getDti(int income, double interestRate) {
		return 0;
	}
}

class TestSingleton extends Singleton {

	public TestSingleton() {
	}
	
	@Override
	public double getDti(int income, double interestRate) {
		return 0;
	}
	
}