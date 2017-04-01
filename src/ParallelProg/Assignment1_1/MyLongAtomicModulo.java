package ParallelProg.Assignment1_1;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by hmahrt on 01.04.17.
 */
class MyLongAtomicModulo extends MyLongAtomic {

    private final long modulo;
    private AtomicLong myAtomicLongModuloCounter = new AtomicLong(0);

    public MyLongAtomicModulo(long modulo) {
        this.modulo = modulo;
    }

	@Override
	public long get() {
		return myAtomicLongModuloCounter.get();
	}

	@Override
	public long incrementAndGet() {
		return myAtomicLongModuloCounter.accumulateAndGet(modulo,
                (counter, mod) -> (counter + 1) % mod);
	}
}
