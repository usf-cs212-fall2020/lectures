import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Demonstrates the impact parallel streams can make on runtime. Please note
 * that benchmarking in Java requires considerable care. You should use
 * something like JMH to perform consistent microbenchmarks.
 */
public class ParallelStreamBenchmark {

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * extremely inefficient approach due to string concatenation and reading the
	 * entire file into memory at once.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsConcat(Path path, String word, Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String text = "";
			String line;
			long count = 0;

			while ((line = reader.readLine()) != null) {
				text += line + "\n";
			}

			for (String token : tokenize.apply(text)) {
				if (token.equals(word)) {
					count++;
				}
			}

			return count;
		}
	}

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * inefficient approach due to reading the entire file into memory at once.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsBuffer(Path path, String word, Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			StringBuffer text = new StringBuffer();
			String line;
			long count = 0;

			while ((line = reader.readLine()) != null) {
				text.append(line);
				text.append("\n");
			}

			for (String token : tokenize.apply(text.toString())) {
				if (token.equals(word)) {
					count++;
				}
			}

			return count;
		}
	}

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * efficient straightforward synchronous approach.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsNormal(Path path, String word, Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			long count = 0;

			while ((line = reader.readLine()) != null) {
				for (String token : tokenize.apply(line)) {
					if (token.equals(word)) {
						count++;
					}
				}
			}

			return count;
		}
	}

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * efficient synchronous streaming approach.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsStream(Path path, String word, Function<String, String[]> tokenize) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				Stream<String> stream = reader.lines();
		) {
			return stream.flatMap(line -> Arrays.stream(tokenize.apply(line)))
					.filter(token -> token.equals(word))
					.count();
		}
	}

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * efficient parallelized streaming approach.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsParallelStream(Path path, String word, Function<String, String[]> tokenize)
			throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				Stream<String> stream = reader.lines();
		) {
			return stream.parallel()
					.flatMap(line -> Arrays.stream(tokenize.apply(line)))
					.filter(token -> token.equals(word))
					.count();
		}
	}

	/**
	 * Demonstrates this class. Do not benchmark more than one method per run or
	 * you will get unreliable results!
	 *
	 * @param args unused
	 * @throws IOException if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		int warmups = 5;
		int repeats = 10;

		SimpleBenchmark concat = ParallelStreamBenchmark::countWordsConcat;
		SimpleBenchmark buffer = ParallelStreamBenchmark::countWordsBuffer;
		SimpleBenchmark normal = ParallelStreamBenchmark::countWordsNormal;
		SimpleBenchmark stream = ParallelStreamBenchmark::countWordsStream;
		SimpleBenchmark parallel = ParallelStreamBenchmark::countWordsParallelStream;

		System.out.print("  Concat: ..............");
		concat.benchmark(0, 1); // takes *forever*

		System.out.print("  Buffer: ");
		buffer.benchmark(warmups, repeats);

		System.out.print("  Normal: ");
		normal.benchmark(warmups, repeats);

		System.out.print("  Stream: ");
		stream.benchmark(warmups, repeats);

		System.out.print("Parallel: ");
		parallel.benchmark(warmups, repeats);

		System.out.print("Parallel: ");
		parallel.benchmark(warmups, repeats);

		System.out.print("  Stream: ");
		stream.benchmark(warmups, repeats);

		System.out.print("  Normal: ");
		normal.benchmark(warmups, repeats);

		System.out.print("  Buffer: ");
		buffer.benchmark(warmups, repeats);

		System.out.print("  Concat: ..............");
		concat.benchmark(0, 1); // takes *forever*
	}

	/**
	 * This is a simple benchmark. It is difficult to get a good benchmark in Java
	 * due to all of the optimization going on while you run the code, so consider
	 * using a microbenchmark library like JMH instead.
	 */
	@FunctionalInterface
	public static interface SimpleBenchmark {

		/** Path to test file. */
		public static final Path PATH = Path.of("1400-0.txt");

		/** Word to look for. */
		public static final String WORD = "great";

		/**
		 * The method to benchmark.
		 *
		 * @param path the path to the file to open
		 * @param word the word to look for
		 * @param tokenize the method to use for tokenization
		 * @return number of times word was found
		 * @throws IOException if an I/O error occurs
		 */
		public long method(Path path, String word, Function<String, String[]> tokenize) throws IOException;

		/**
		 * Splits a line into tokens by any non-alphabetic character.
		 *
		 * @param line the line to split into tokens
		 * @return the tokens
		 */
		public static String[] tokenize(String line) {
			return line.toLowerCase().split("[^\\p{Alpha}]+");
		}

		/**
		 * Default benchmark implementation.
		 *
		 * @param warmups number of warmup rounds
		 * @param repeats number of timed rounds
		 * @throws IOException if an I/O error occurs
		 */
		public default void benchmark(int warmups, int repeats) throws IOException {
			long count = 0; // just to make sure we always use the result

			for (int i = 0; i < warmups; i++) {
				System.out.print(".");
				count = Math.max(count, method(PATH, WORD, SimpleBenchmark::tokenize));
			}

			Instant start = Instant.now();

			for (int i = 0; i < repeats; i++) {
				System.out.print(".");
				count = Math.max(count, method(PATH, WORD, SimpleBenchmark::tokenize));
			}

			Duration elapsed = Duration.between(start, Instant.now());
			double average = (double) elapsed.toMillis() / repeats;
			double seconds = average / Duration.ofSeconds(1).toMillis();

			String format = "Found %d occurrences of \"%s\" in %f seconds.%n";
			System.out.printf(format, count, WORD, seconds);
		}

	}

}
