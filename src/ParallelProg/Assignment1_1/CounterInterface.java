package ParallelProg.Assignment1_1;

public interface CounterInterface {

    /**
     * Gets the current value of the counter.
     * @return current value
     */
    long get();

    /**
     * Increments the counter by 1 and returns the new
     * value of the counter.
     * @return new value
     */
	long incrementAndGet();

    /**
     * Checks if the counter contains the desired value.
     * Prints a message.
     * @param desired desired value of the counter
     */
	default void check(long desired) {
	    if (differs(desired))
	        System.out.println("Counter differs from expected value!");
		System.out.println("Expected: " + desired + ". Actual: " + this.get());
	}

    /**
     * Checks if the counter differs from the desired value.
     * @param desired desired value of the counter
     * @return True, if values are not matching. Otherwise false.
     */
    default boolean differs(long desired) {
        return get() != desired;
    }
}
