import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

/**
 * Demonstrates basic multithreading, and illustrates how to break up a problem
 * into subproblems. Also used to motivate the inefficiency of constantly
 * creating new threads instead of reusing them.
 */
public class RandomArrayTotal {

	/**
	 * Calculates a subtotal in an array. Does no validation of parameters, so
	 * results will vary when invalid indices are provided.
	 *
	 * @param numbers the array of numbers to subtotal
	 * @param start the index of array to start subtotal
	 * @param chunk the number of values to subtotal
	 * @return subtotal of numbers from index {@code start} (inclusive) to
	 *         {@code start + chunk} (exclusive)
	 */
	public static long subtotal(int[] numbers, int start, int chunk) {
		long total = 0;

		for (int i = start; i < start + chunk; i++) {
			total = total + numbers[i];
		}

		return total;
	}

	/**
	 * Calculates total of values in an array.
	 *
	 * @param numbers array of numbers to total
	 * @return total of numbers in array
	 */
	public static long total(int[] numbers) {
		return subtotal(numbers, 0, numbers.length);
	}

	/**
	 * Calculates the total of an array using multithreading. Used to demonstrate
	 * the cost of creating/destroying thread objects versus using a work queue.
	 *
	 * @param numbers array of numbers to total
	 * @param threads number of worker threads to create
	 * @return total of numbers in array
	 * @throws InterruptedException if interrupted, see {@link Thread#join()}
	 */
	public static long total(int[] numbers, int threads) throws InterruptedException {
		// make sure we have valid number of threads
		if (threads < 1) {
			throw new InvalidParameterException("The number of worker threads must be 1 or greater.");
		}
		else if (threads > numbers.length) {
			throw new InvalidParameterException("Cannot have more worker threads than numbers to total.");
		}

		// create an array of workers
		ArrayWorker[] workers = new ArrayWorker[threads];

		// calculate how to split up the problem
		int chunk = numbers.length / workers.length;
		int remainder = numbers.length % workers.length;
		int last = workers.length - 1;

		long total = 0;

		assert chunk > 0;
		assert remainder >= 0;

		// create and start the worker threads
		for (int i = 0; i < last; i++) {
			workers[i] = new ArrayWorker(numbers, i * chunk, chunk);
			workers[i].start();
		}

		// account for any remainder
		workers[last] = new ArrayWorker(numbers, last * chunk, chunk + remainder);
		workers[last].start();

		// wait for workers to finish and add up subtotal
		for (ArrayWorker worker : workers) {
			worker.join();
			total = total + worker.subtotal;
		}

		return total;
	}

	/**
	 * Uses the {@link RandomArrayTotal#subtotal(int[], int, int)} method to
	 * generate a subtotal of an array.
	 */
	private static class ArrayWorker extends Thread {

		/** The array of numbers to subtotal */
		private final int[] numbers;

		/** The starting index in the numbers array */
		private final int start;

		/** The ending index in the numbers array */
		private final int end;

		/** The calculated subtotal from start to end in the numbers array */
		private long subtotal;

		/**
		 * Initializes this worker.
		 *
		 * @param numbers the array of numbers
		 * @param start the starting index
		 * @param end the ending index
		 */
		public ArrayWorker(int[] numbers, int start, int end) {
			this.numbers = numbers;
			this.start = start;
			this.end = end;

			this.subtotal = 0;
		}

		@Override
		public void run() {
			subtotal = subtotal(numbers, start, end);
		}
	}

	/**
	 * Compares our single threaded and multi threaded approach. Notice that we do
	 * not see a speedup when we increase the number of threads for small sizes.
	 * Do you understand why?
	 *
	 * @param args unused
	 * @throws InterruptedException if interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		// create an array of random integers
		int[] numbers = new Random().ints(10).toArray();

		System.out.println(Arrays.toString(numbers));
		System.out.println();

		System.out.println(total(numbers));
		System.out.println(total(numbers, 5));
		System.out.println();

		int size = 10000000;
		long keep = 0;

		keep = Math.max(keep, benchmark(size, 1));
		keep = Math.max(keep, benchmark(size, 2));
		keep = Math.max(keep, benchmark(size, 3));
		keep = Math.max(keep, benchmark(size, 5));
		keep = Math.max(keep, benchmark(size, 8));

		System.out.println();

		keep = Math.max(keep, benchmark(size, 8));
		keep = Math.max(keep, benchmark(size, 5));
		keep = Math.max(keep, benchmark(size, 3));
		keep = Math.max(keep, benchmark(size, 2));
		keep = Math.max(keep, benchmark(size, 1)); // note how varies from first run

		System.out.println("\nMeaningless number: " + keep);
	}

	/**
	 * Rough benchmarking estimate. Use a benchmark package for better results.
	 *
	 * @param size the size of the random array
	 * @param threads the number of worker threads
	 * @return placeholder value
	 * @throws InterruptedException if interrupted
	 */
	private static long benchmark(int size, int threads) throws InterruptedException {
		int warmup = 10;
		int runs = 30;

		int[] numbers = new Random().ints(size).toArray();

		long placeholder = 0;
		double average = 0;

		Instant start;
		Duration elapsed;

		for (int i = 0; i < warmup; i++) {
			placeholder = Math.max(placeholder, total(numbers, threads));
		}

		for (int i = 0; i < runs; i++) {
			start = Instant.now();
			placeholder = Math.max(placeholder, total(numbers, threads));
			elapsed = Duration.between(start, Instant.now());
			average += elapsed.toNanos();
		}

		average /= runs;
		average /= Duration.ofSeconds(1).toNanos();

		System.out.printf("%.05f seconds average for %d numbers and %d threads%n", average, size, threads);

		return placeholder;
	}
}
