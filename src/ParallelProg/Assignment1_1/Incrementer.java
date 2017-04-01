package ParallelProg.Assignment1_1;

import java.util.concurrent.CountDownLatch;

public class Incrementer implements Runnable {
    private final CountDownLatch start, end;
    private final CounterInterface counter;
    private final int runs;

    public Incrementer(CountDownLatch start, CountDownLatch end, CounterInterface counter, int runs) {
        this.start = start;
        this.end = end;
        this.counter = counter;
        this.runs = runs;
    }

    public void run() {
        try {
            start.await();
            for (int i = 0; i < runs; i++) {
                counter.incrementAndGet();
            }
            end.countDown();
        }
        catch (Exception e) {

        }
    }
}

