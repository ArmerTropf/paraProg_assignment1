import ParallelProg.Assignment1_1.*;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment1_1Test {
    final static int numOfIncrementers = 150;
    final static int numOfRuns = 1000;
    final static int fixedThreadsNumber = 3;
    final static long modulo = 16;

    @Test
    public void TestAssignment1_1_ManualThreadExecution_MyLong() {
        System.out.println("Manual thread execution + MyLong:");

        // arrange
        CounterInterface counter = new MyLong();

        // act
        manualThreadExecution(counter);

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc);

        printRuler();
    }

    @Test
    public void TestAssignment1_1_ManualThreadExecution_MyLongAtomic() {
        System.out.println("Manual thread execution + MyLongAtomic:");

        // arrange
        CounterInterface counter = new MyLongAtomic();

        // act
        manualThreadExecution(counter);

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc);

        printRuler();
    }

    @Test
    public void TestAssignment1_1_ManualThreadExecution_MyLongAtomicModulo() {
        System.out.println("Manual thread execution + MyLongAtomicModulo:");

        // arrange
        CounterInterface counter = new MyLongAtomicModulo(modulo);

        // act
        manualThreadExecution(counter);

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc % modulo);

        printRuler();
    }

    private void manualThreadExecution(CounterInterface counter) {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numOfIncrementers);

        Thread[] Incrementers = new Thread[numOfIncrementers];

        for (int i = 0; i < numOfIncrementers; i++) {
            Incrementer incrementer = new Incrementer(startLatch, endLatch, counter, numOfRuns);
            Incrementers[i] = new Thread(incrementer);
        }

        for (int i = 0; i < numOfIncrementers; i++) {
            Incrementers[i].start();
        }

        waitForLatches(startLatch, endLatch);
    }


    @Test
    public void TestAssignment1_1_CachedThreadPool_MyLong() {
        System.out.println("CachedThreadPool + MyLong:");

        // arrange
        CounterInterface counter = new MyLong();

        // act
        execute(counter, Executors.newCachedThreadPool());

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc);

        printRuler();
    }


    @Test
    public void TestAssignment1_1_CachedThreadPool_MyLongAtomic() {
        System.out.println("CachedThreadPool + MyLongAtomic:");

        // arrange
        CounterInterface counter = new MyLongAtomic();

        // act
        execute(counter, Executors.newCachedThreadPool());

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc);

        printRuler();
    }

    @Test
    public void TestAssignment1_1_CachedThreadPool_MyLongAtomicModulo() {
        System.out.println("CachedThreadPool + MyLongAtomicModulo:");

        // arrange
        CounterInterface counter = new MyLongAtomicModulo(modulo);

        // act
        execute(counter, Executors.newCachedThreadPool());

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc % modulo);

        printRuler();
    }

    private void execute(CounterInterface counter, ExecutorService executorService) {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numOfIncrementers);

        for (int i = 0; i < numOfIncrementers; i++) {
            Incrementer incrementer = new Incrementer(startLatch, endLatch, counter, numOfRuns);
            executorService.submit(incrementer);
        }

        waitForLatches(startLatch, endLatch);

        executorService.shutdown();
    }

    private void waitForLatches(CountDownLatch startLatch, CountDownLatch endLatch) {
        try {
            startLatch.countDown();
            endLatch.await();
        } catch (Exception e) {

        }
    }

    private void printRuler() {
        System.out.println("=============================================");
    }

    @Test
    public void TestAssignment1_1_SingleThreadExecutor_MyLong() {
        System.out.println("SingleThread + MyLong:");

        // arrange
        CounterInterface counter = new MyLong();

        // act
        execute(counter, Executors.newSingleThreadExecutor());

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc);

        printRuler();
    }

    @Test
    public void TestAssignment1_1_SingleThreadExecutor_MyLongAtomic() {
        System.out.println("SingleThread + MyLongAtomic:");

        // arrange
        CounterInterface counter = new MyLongAtomic();

        // act
        execute(counter, Executors.newSingleThreadExecutor());

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc);

        printRuler();
    }

    @Test
    public void TestAssignment1_1_SingleThreadExecutor_MyLongAtomicModulo() {
        System.out.println("SingleThread + MyLongAtomicModulo:");

        // arrange
        CounterInterface counter = new MyLongAtomicModulo(modulo);

        // act
        execute(counter, Executors.newSingleThreadExecutor());

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc % modulo);

        printRuler();
    }

    @Test
    public void TestAssignment1_1_FixedThreadPool_MyLong() {
        System.out.println("FixedThreadPool + MyLong:");

        // arrange
        CounterInterface counter = new MyLong();

        // act
        execute(counter, Executors.newFixedThreadPool(fixedThreadsNumber));

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc);

        printRuler();
    }

    @Test
    public void TestAssignment1_1_FixedThreadPool_MyLongAtomic() {
        System.out.println("FixedThreadPool + MyLongAtomic:");

        // arrange
        CounterInterface counter = new MyLongAtomic();

        // act
        execute(counter, Executors.newFixedThreadPool(fixedThreadsNumber));

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc);

        printRuler();
    }

    @Test
    public void TestAssignment1_1_FixedThreadPool_MyLongAtomicModulo() {
        System.out.println("FixedThreadPool + MyLongAtomicModulo:");

        // arrange
        CounterInterface counter = new MyLongAtomicModulo(modulo);

        // act
        execute(counter, Executors.newFixedThreadPool(fixedThreadsNumber));

        // assert
        long totalInc = numOfRuns * numOfIncrementers;
        counter.check(totalInc % modulo);

        printRuler();
    }
}
