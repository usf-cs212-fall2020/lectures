import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

public class RandomArrayTotal {

	public static long subtotal(int[] numbers, int start, int chunk) {
		long total = 0;

		for (int i = start; i < start + chunk; i++) {
			total = total + numbers[i];
		}

		return total;
	}

	public static long total(int[] numbers) {
		return subtotal(numbers, 0, numbers.length);
	}

	public static long total(int[] numbers, int threads) throws InterruptedException {
		if (threads < 1) {
			throw new InvalidParameterException("The number of worker threads must be 1 or greater.");
		}
		else if (threads > numbers.length) {
			throw new InvalidParameterException("Cannot have more worker threads than numbers to total.");
		}

		long total = 0;

		// TODO

		return total;
	}

	public static void main(String[] args) throws InterruptedException {
		int[] numbers = new Random().ints(10).toArray();

		System.out.println(Arrays.toString(numbers));
		System.out.println();

		System.out.println(total(numbers));
		System.out.println(total(numbers, 5));
		System.out.println();

		// int size = 1000;
		// long keep = 0;
		//
		// keep = Math.max(keep, benchmark(size, 1));
		// keep = Math.max(keep, benchmark(size, 2));
		// keep = Math.max(keep, benchmark(size, 5));
		//
		// keep = Math.max(keep, benchmark(size, 5));
		// keep = Math.max(keep, benchmark(size, 2));
		// keep = Math.max(keep, benchmark(size, 1));
		//
		// System.out.println("\nMeaningless number: " + keep);
	}

	public static long benchmark(int size, int threads) throws InterruptedException {
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
