package ParallelProg.Assignment1_1;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by hmahrt on 01.04.17.
 */
public class MyLongAtomic extends MyLong {

    private AtomicLong myAtomicLongCounter = new AtomicLong(0);

	@Override
	public long get() {
		return myAtomicLongCounter.get();
	}

	@Override
	public long incrementAndGet() {
		return myAtomicLongCounter.incrementAndGet();
	}

}
