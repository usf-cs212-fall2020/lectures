import java.util.Arrays;

/**
 * Demonstrates simple changes in thread state for a main thread and a single
 * worker thread.
 */
public class ThreadStateDemo {

	/** Output format */
	public static final String FORMAT = "[%-11s] Run by: %-8s  Parent: %s is %-8s  Worker: %s is %s%n";

	/** The parent thread (will be the main thread) */
	private Thread parentThread;

	/** The worker thread */
	private Thread workerThread;

	/**
	 * Gets the current main thread, spawns a new worker thread, and outputs the
	 * changes in thread state.
	 *
	 * @throws InterruptedException if a thread is interrupted
	 */
	public ThreadStateDemo() throws InterruptedException {
		parentThread = Thread.currentThread();

		workerThread = new Worker();
		output("AFTER NEW");

		workerThread.start();
		output("AFTER START");
		calculate(500);

		workerThread.join();
		output("AFTER JOIN");
	}

	/**
	 * Worker thread that performs some meaningless computation, that should take
	 * longer than the main thread.
	 */
	private class Worker extends Thread {
		@Override
		public void run() {
			calculate(1000);
			output("AFTER CALC");
		}
	}

	/**
	 * Performs some meaningless computation. Used to keep threads busy without
	 * calling sleep() or wait() explicitly.
	 *
	 * @param size the size of array to initialize and sort
	 */
	public static void calculate(int size) {
		double[] junk = new double[size];

		for (int i = 0; i < junk.length; i++) {
			junk[i] = Math.random();
		}

		Arrays.sort(junk);
	}

	/**
	 * Outputs the state of the main (parent) thread and worker thread.
	 *
	 * @param message the message to distinguish output
	 */
	public void output(String message) {
		System.out.printf(FORMAT, message, Thread.currentThread().getName(), parentThread.getName(),
				parentThread.getState().toString(), workerThread.getName(), workerThread.getState().toString());
	}

	/**
	 * Starts the simple thread state demo.
	 *
	 * @param args unused
	 * @throws InterruptedException if a thread is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		new ThreadStateDemo();
	}
}
