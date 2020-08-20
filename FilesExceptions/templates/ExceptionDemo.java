import java.util.Scanner;

@SuppressWarnings({ "resource", "unused" })
public class ExceptionDemo {

	private static int calcPercentage(int earned, int possible) {
		return 100 * earned / possible;
	}

	private static void printResult(int earned, int possible, int percentage) {
		System.out.printf("%d/%d = %d%% %n", earned, possible, percentage);
	}

	public static void uncaughtDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = new Scanner(System.in);

		// TODO Implement uncaughtDemo method
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	public static void validationDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = new Scanner(System.in);

		// TODO Implement validationDemo method
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	public static void catchAllDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = null;

		// TODO Implement catchAllDemo method
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	public static void resourcefulDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		// TODO Implement resourcefulDemo method
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	public static void main(String[] args) {
		uncaughtDemo();
		// validationDemo();
		// catchAllDemo();
		// resourcefulDemo();
	}
}
