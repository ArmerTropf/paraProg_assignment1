package paraProg_assignment1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;

import org.w3c.dom.css.Counter;


/**
 * 
 * @author Schriever, Mahrt, Günster
 *
 */

public class Assignment1 {
	private final static int INCREMENTERS = 7;
	private final static int RUNS = 55;

	protected static class Incrementer implements Runnable {
		private final CountDownLatch start, end;
		private final CounterInterface counter;

		public Incrementer(CountDownLatch start, CountDownLatch end, CounterInterface counter) {
			this.start = start;
			this.end = end;
			this.counter = counter;
		}

		public void run() {
			try {
				start.await();
				for (int i = 0; i < RUNS; i++) {
					counter.incrementAndGet();
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

//		CounterInterface counter =  new MyLong();
//		CounterInterface counter =  new MyLongAtomic();
		CounterInterface counter =  new MyLongAtomicModulo();
		
		for (int i = 0; i < INCREMENTERS; i++) {

			myExCached.submit(new Incrementer(startLatch, endLatch, counter ));
		
			
			//Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch, new MyLongAtomicModulo()));
			//Incrementers[i].start();
			
		}
		try {
			startLatch.countDown();
			
			endLatch.await();
			long totalInc = RUNS * INCREMENTERS;
			counter.check(totalInc);
		} catch (Exception e) {
		}
		myExCached.shutdown();

	}
}




interface CounterInterface {

	
	long get();
	long incrementAndGet();
	default void check(long desired) {
		System.out.println("Finished after " + desired + " increments with counter = " + this.get());
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
		
}

class MyLongAtomicModulo extends MyLongAtomic {
	protected AtomicLong myAtomicLongModuloCounter = new AtomicLong(0);

	@Override
	public long get() {

		return myAtomicLongModuloCounter.get();
	}

	@Override
	public long incrementAndGet() {
		return myAtomicLongModuloCounter.accumulateAndGet(16, new LongBinaryOperator() {
			
			@Override
			public long applyAsLong(long left, long right) {
				
				return (left+1) % right;
			}
		});
		//myAtomicLongModuloCounter.set(myAtomicLongModuloCounter.incrementAndGet() % 16);
		//return myAtomicLongModuloCounter.get();
	}

	@Override
	public void check(long desired) {
		System.out.println("Expected: " + desired % 16 + " actual: " + this.get());
	}

}