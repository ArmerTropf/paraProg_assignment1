package ParallelProg.Assignment1_1;

public class MyLong implements CounterInterface {

    private long myCounter = 0;

	@Override
	public long get() {
		return myCounter;
	}

	@Override
	public long incrementAndGet() {
		return ++this.myCounter;
	}

}
