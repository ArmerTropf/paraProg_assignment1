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
	private final static int INCREMENTERS = 7;
	private final static int RUNS = 5;
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
		//Thread[] Incrementers = new Thread[INCREMENTERS];
		
		ExecutorService myExCached = Executors.newCachedThreadPool();
		//ExecutorService myExCached = Executors.newSingleThreadExecutor();
		//ExecutorService myExCached = Executors.newFixedThreadPool(2);

	
		for (int i = 0; i < INCREMENTERS; i++) {

			//myExCached.submit(new Incrementer(startLatch, endLatch, new MyLong()));
			myExCached.submit(new Incrementer(startLatch, endLatch, new MyLongAtomic()));
			//myExCached.submit(new Incrementer(startLatch, endLatch, new MyLongAtomicModulo()));
			
			//Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch, new MyLongAtomicModulo()));
			//Incrementers[i].start();
			
		}
		try {
			startLatch.countDown();
			
			endLatch.await();
			long totalInc = RUNS * INCREMENTERS;
			System.out.println("Finished after " + totalInc + " increments with counter = " + Incrementer.myLong_counter.get());
		} catch (Exception e) {
		}
		myExCached.shutdown();

	}
}

interface CounterInterface {

	
	long get();
	long incrementAndGet();
	default void check(long desired) {
		
	}
}

class MyLong implements CounterInterface {

	private long myCounter = 0;
	
	@Override
	public long get() {
		return myCounter;
	}

	@Override
	public long incrementAndGet() {
		this.myCounter++;
		
		return this.myCounter;	
	}

	@Override
	public void check(long desired) {

	}

}

class MyLongAtomic extends MyLong {
	
	
	protected AtomicLong myAtomicLongCounter = new AtomicLong(0);
	
	@Override
	public long get() {

		return myAtomicLongCounter.get();
	}

	@Override
	public long incrementAndGet() {
		myAtomicLongCounter.incrementAndGet();
		
		return myAtomicLongCounter.get();
	}

	@Override
	public void check(long desired) {
		
	}
		
}

class MyLongAtomicModulo extends MyLongAtomic {
	protected AtomicLong myAtomicLongModuloCounter = new AtomicLong(0);

	@Override
	public long get() {

		return myAtomicLongModuloCounter.get();
	}

	@Override
	public long incrementAndGet() {
		myAtomicLongModuloCounter.set(myAtomicLongModuloCounter.incrementAndGet() % 16);
		return myAtomicLongModuloCounter.get();
	}

	@Override
	public void check(long desired) {

	}

}