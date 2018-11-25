package nl.yasmijn.utils;

public class Checker {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(elevenTest("491673841"));
		System.out.println(elevenTest(491673841));
		double MILLION = 1000000.0;
		int cnt = 0;
		long t0 = System.nanoTime();
		for (int i = 491673841; i < 491673841; i++) {
			if (elevenTest(Integer.toString(i)))
				cnt++;
		}
		long t1 = System.nanoTime();
		System.out.println((t1 - t0) / MILLION);
		System.out.println(cnt);
		cnt = 0;
		t0 = System.nanoTime();
		for (int i = 491673841; i < 491673841; i++) {
			if (elevenTest(i))
				cnt++;
		}
		t1 = System.nanoTime();
		System.out.println((t1 - t0) / MILLION);
		System.out.println(cnt);
		Threader1 th1 = new Threader1();
		Threader2 th2 = new Threader2();
		th1.start();
		th2.start();
		th1.join();
		if (th2.isAlive()) {
			System.out.println("Gotcha");
			th2.join();
		}
		System.out.println("String thread: "+th1.t);
		System.out.println("int thread: "+th2.t);
	}

	public static boolean elevenTest(String number) {
		int sum = 0;
		for (int pos = number.length() - 1; pos >= 0; pos--) {
			sum += (number.charAt(pos) - '0') * (number.length() - pos);
		}
		return sum % 11 == 0;
	}

	public static boolean elevenTest(int number) {
		int sum = 0;
		int pos = 1;
		while (number > 0) {
			sum += (number % 10) * pos++;
			number /= 10;
		}
		return sum % 11 == 0;
	}
}

class Threader1 extends Thread {
	int cnt = 0;
	double t = 0.0;

	public void run() {
//		System.out.println("Started1");
		cnt = 0;
		long t0 = System.nanoTime();
		for (int i = 491673841; i < 641673841; i++) {
			if (Checker.elevenTest(i))
				cnt++;
		}
		long t1 = System.nanoTime();
		t = ((t1 - t0) / 1000000.0);
	}
}

class Threader2 extends Thread {
	int cnt = 0;
	double t = 0.0;

	public void run() {
//		System.out.println("Started2");
		cnt = 0;
		long t0 = System.nanoTime();
		for (int i = 491673841; i < 751673841; i++) {
			if (Checker.elevenTest(Integer.toString(i)))
				cnt++;
		}
		long t1 = System.nanoTime();
		t = ((t1 - t0) / 1000000.0);
	}
}
