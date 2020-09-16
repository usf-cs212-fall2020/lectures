import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import java.util.stream.Stream;

public class ParallelStreamBenchmark {

	public static long countWordsConcat(Path path, String word, Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			long count = 0;

			// TODO

			return count;
		}
	}

	public static long countWordsBuffer(Path path, String word, Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			long count = 0;

			// TODO

			return count;
		}
	}

	public static long countWordsNormal(Path path, String word, Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			long count = 0;

			// TODO

			return count;
		}
	}

	public static long countWordsStream(Path path, String word, Function<String, String[]> tokenize) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				Stream<String> stream = reader.lines();
		) {
			// TODO
			return -1;
		}
	}

	public static long countWordsParallelStream(Path path, String word, Function<String, String[]> tokenize)
			throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				Stream<String> stream = reader.lines();
		) {
			// TODO
			return -1;
		}
	}

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

	@FunctionalInterface
	public static interface SimpleBenchmark {
		public static final Path PATH = Path.of("1400-0.txt");
		public static final String WORD = "great";

		public long method(Path path, String word, Function<String, String[]> tokenize) throws IOException;

		public static String[] tokenize(String line) {
			return line.toLowerCase().split("[^\\p{Alpha}]+");
		}

		public default void benchmark(int warmups, int repeats) throws IOException {
			long count = 0;

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
