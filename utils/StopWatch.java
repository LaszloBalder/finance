package nl.yasmijn.utils;

public class StopWatch {
	private String name;
	int lapCount = 0;
	private static final int DEFAULTLAPS = 32;
	private static final double MILLION = 1000000.0;
	private long laps[];
	private boolean running = false;

	public StopWatch(String name, int laps) {
		this.name = name;
		this.laps = new long[laps + 1];
	}

	public StopWatch(String name) {
		this(name, DEFAULTLAPS);
	}

	public StopWatch() {
		this("", DEFAULTLAPS);
	}

	public String getName() {
		return name;
	}

	/**
	 * Reset the stop watch and start it
	 * 
	 */
	public void start() {
		lapCount = 0;
		running = true;
		laps[lapCount] = System.nanoTime();
	}

	public void lap() {
		lapCount++;
		if (lapCount < laps.length) {
			laps[lapCount] = System.nanoTime();
		} else {
			laps[laps.length - 1] = System.nanoTime();
		}
	}

	public double getLapTimeInMillis(int lap) {
		return (laps[lap] - laps[lap - 1]) / MILLION;
	}

	public boolean isRunning() {
		return running;
	}
	
	public void printLapTimes() {
		for (int i = 1; i <= lapCount; i++) {
			System.out.println(i + " :"+ getLapTimeInMillis(i));
		}
	}

	/**
	 * Stop the stop watch
	 */
	public void stop() {
		lap();
		running = false;
	}

	/**
	 * @return number of laps
	 */
	public int getLaps() {
		return lapCount;
	}

	public double getTimeInMillis() {
		long nanos = 0;
		if (laps.length > 1) {
			if (lapCount > laps.length) {
				nanos = (laps[laps.length - 1] - laps[0]);
			} else {
				nanos = (laps[lapCount] - laps[0]);
			}
		}
		return nanos / MILLION;
	}

	public double getAverageLapTimeInMillis() {
		return getTimeInMillis() / getLaps();
	}

}
