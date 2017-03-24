package paraProg_assignment1;

import java.util.concurrent.CountDownLatch;

public class Assignment1 {
	private final static int INCREMENTERS = 20;
	private final static int RUNS = 50000;
	private static long counter = 0;

	protected static class Incrementer implements Runnable {
		private final CountDownLatch start, end;

		public Incrementer(CountDownLatch start, CountDownLatch end) {
			this.start = start;
			this.end = end;
		}

		public void run() {
			try {
				start.await();
				for (int i = 0; i < RUNS; i++) {
					counter++;
				}
				end.countDown();
			} catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) {

		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
		Thread[] Incrementers = new Thread[INCREMENTERS];
		for (int i = 0; i < INCREMENTERS; i++) {
			Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch));
			Incrementers[i].start();
		}
		try {
			System.out.println("Starting with counter = " + counter);
			startLatch.countDown();
			endLatch.await();
			long totalInc = RUNS * INCREMENTERS;
			System.out.println("Finished after " + totalInc + " increments with counter = " + counter);
		} catch (Exception e) {
		}
	}
}
