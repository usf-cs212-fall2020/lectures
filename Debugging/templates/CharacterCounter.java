import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CharacterCounter {

	private static int SIZE = 1000;

	@SuppressWarnings("unused")
	public static int countNoDebug(Path file) {
		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			// TODO
		}
		catch (IOException e) {

		}

		return total;
	}

	public static int countAssertions(Path file) {
		// TODO
		return -1;
	}

	public static int countWithPrintln(Path file) {
		// TODO
		return -1;
	}

	public static int countWithDebug(Path file) {
		// TODO
		return -1;
	}

	public static int countWithLogging(Path file) {
		// TODO
		return -1;
	}

	public static int countCharacters(Path file) {
		// TODO
		return -1;
	}

	public static void main(String[] args) {
		Path path1 = Path.of("src");
		Path path2 = Path.of("README.md");

		System.out.println("No Output Messages:");
		System.out.println(countNoDebug(path1));
		System.out.println(countNoDebug(path2));
		System.out.println();

		// System.out.println("With Assertions:");
		// System.out.println(countAssertions(path1));
		// System.out.println(countAssertions(path2));
		// System.out.println();

		// System.out.println("With Println Statements:");
		// System.out.println(countWithPrintln(path1));
		// System.out.println(countWithPrintln(path2));
		// System.out.println();

		// Debug.on = true;
		// System.out.println("With Debug Statements:");
		// System.out.println(countWithDebug(path1));
		// System.out.println(countWithDebug(path2));
		// System.out.println();

		// System.out.println("With Logging:");
		// System.out.println(countWithLogging(path1));
		// System.out.println(countWithLogging(path2));
		// System.out.println();

		// System.out.println("Final Version:");
		// System.out.println(countCharacters(path1));
		// System.out.println(countCharacters(path2));
		// System.out.println();
	}
}
