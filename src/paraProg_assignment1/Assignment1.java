package paraProg_assignment1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 
 * @author Schriever, Mahrt, Günster
 *
 */

public class Assignment1 {
	private final static int INCREMENTERS = 500;
	private final static int RUNS = 500000;
	// private static long counter = 0;

	protected static class  Incrementer implements Runnable {
		private final CountDownLatch start, end;
		private static MyLong myLong_counter;

		public Incrementer(CountDownLatch start, CountDownLatch end, MyLong counter) {
			this.start = start;
			this.end = end;
			Incrementer.myLong_counter = counter;
		}

		public void run() {
			try {
				start.await();
				for (int i = 0; i < RUNS; i++) {
					myLong_counter.incrementAndGet();
				}
				end.countDown();	
			} 
			catch (Exception e) {
			}
						
		}
	}

	public static void main(String[] args) {

		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
		Thread[] Incrementers = new Thread[INCREMENTERS];
		
		//ExecutorService myExCached = Executors.newCachedThreadPool();
		//ExecutorService myExCached = Executors.newSingleThreadExecutor();
		//ExecutorService myExCached = Executors.newFixedThreadPool(2);

	
		for (int i = 0; i < INCREMENTERS; i++) {

			//myExCached.submit(new Incrementer(startLatch, endLatch, new MyLong()));
			Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch, new MyLong()));
			Incrementers[i].start();
		}
		try {
			

			startLatch.countDown();
			endLatch.await();
			long totalInc = RUNS * INCREMENTERS;
			System.out.println("Finished after " + totalInc + " increments with counter = " + Incrementer.myLong_counter.get());
		} catch (Exception e) {
		}
		//myExCached.shutdown();

	}
}

interface CounterInterface {
	long get();

	long incrementAndGet();

	void check(long desired);
}

class MyLong extends MyLongAtomic implements CounterInterface {

	private long myCounter = 0;
			
	@Override
	public long get() {
		//return myCounter;
		return myAtomicLongCounter.get();
	}

	@Override
	public long incrementAndGet() {
		//this.myCounter++;
		//return this.myCounter;
		
		this.myAtomicLongCounter.incrementAndGet();
		return myAtomicLongCounter.get();
		
		
	}

	@Override
	public void check(long desired) {
		// TODO Auto-generated method stub

	}

}

class MyLongAtomic {
	
	
	protected AtomicLong myAtomicLongCounter = new AtomicLong(0);
		
}