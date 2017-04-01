import ParallelProg.Assignment1_1.CounterInterface;
import ParallelProg.Assignment1_1.Incrementer;
import ParallelProg.Assignment1_1.MyLong;

import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment1_1Test {
    final static int numOfIncrementers = 5;
    final static int numOfRuns = 1000;

    @Test
    public void TestAssignment1_1_ManualThreadExecution_MyLong() {

        System.out.println("Manual thread execution + MyLong:");

        // arrange
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numOfIncrementers);
        CounterInterface counter = new MyLong();

        Thread[] Incrementers = new Thread[numOfIncrementers];

        for (int i = 0; i < numOfIncrementers; i++) {
            Incrementer incrementer = new Incrementer(startLatch, endLatch, counter, numOfRuns);
            Incrementers[i] = new Thread(incrementer);
        }

        // act
        for (int i = 0; i < numOfIncrementers; i++) {
            Incrementers[i].start();
        }

        try {
            startLatch.countDown();
            endLatch.await();

            long totalInc = numOfRuns * numOfIncrementers;
            counter.check(totalInc);
        } catch (Exception e) {

        }

        System.out.println("========================================");
    }

    @Test
    public void TestAssignment1_1_ManualThreadExecution_MyAtomicLong() {

    }

    @Test
    public void TestAssignment1_1_ManualThreadExecution_MyAtomicModulo() {

    }

    @Test
    public void TestAssignment1_1_CachedThreadPool_MyLong() {

        ExecutorService myExCached = Executors.newCachedThreadPool();

//        myExCached.submit(new Incrementer(startLatch, endLatch, counter, numOfRuns));

    }

    @Test
    public void TestAssignment1_1_CachedThreadPool_MyAtomicLong() {

    }

    @Test
    public void TestAssignment1_1_CachedThreadPool_MyAtomicModulo() {

    }

    @Test
    public void TestAssignment1_1_SingleThreadExecutor_MyLong() {
        ExecutorService myExCached = Executors.newSingleThreadExecutor();
    }

    @Test
    public void TestAssignment1_1_SingleThreadExecutor_MyAtomicLong() {

    }

    @Test
    public void TestAssignment1_1_SingleThreadExecutor_MyAtomicModulo() {

    }

    final static int numOfThreads = 2;

    @Test
    public void TestAssignment1_1_FixedThreadPool_MyLong() {
        ExecutorService myExCached = Executors.newFixedThreadPool(2);
    }

    @Test
    public void TestAssignment1_1_FixedThreadPool_MyAtomicLong() {

    }

    @Test
    public void TestAssignment1_1_FixedThreadPool_MyAtomicModulo() {

    }
}
