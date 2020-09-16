import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.function.Predicate;

/**
 * Demonstrates lambda expressions using the {@link PathMatcher} functional
 * interface.
 */
public class PathMatcherDemo {

	/**
	 * Demonstrates the examples in the lambda expression lecture slides.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		Path path1 = Path.of("hello.txt");
		Path path2 = Path.of("txt.hello");

		// anonymous inner class
		PathMatcher m1 = new PathMatcher() {
			@Override
			public boolean matches(Path p) {
				return p.toString().endsWith(".txt");
			}
		};

		System.out.println(path1 + ": " + m1.matches(path1));
		System.out.println(path2 + ": " + m1.matches(path2));
		System.out.println();

		// verbose lambda expression
		PathMatcher m2 = (Path p) -> {
			return p.toString().endsWith(".txt");
		};

		System.out.println(path1 + ": " + m2.matches(path1));
		System.out.println(path2 + ": " + m2.matches(path2));
		System.out.println();

		// compact lambda expression
		PathMatcher m3 = p -> p.toString().endsWith(".txt");

		System.out.println(path1 + ": " + m3.matches(path1));
		System.out.println(path2 + ": " + m3.matches(path2));
		System.out.println();

		// changing the identifier type
		// only the function "shape" matters (not the name)
		Predicate<Path> m4 = p -> p.toString().endsWith(".txt");

		System.out.println(path1 + ": " + m4.test(path1));
		System.out.println(path2 + ": " + m4.test(path2));
		System.out.println();

		// can the var keyword be used for anonymous inner classes?
		// yes, the type is given by the constructor call
		var m5 = new PathMatcher() {
			@Override
			public boolean matches(Path p) {
				return p.toString().endsWith(".txt");
			}
		};

		System.out.println(path1 + ": " + m5.matches(path1));
		System.out.println(path2 + ": " + m5.matches(path2));
		System.out.println();

		// can the var keyword be used for lambda expressions?
		// no, lambda expressions have no standalone type and require a target type
		// https://stackoverflow.com/questions/49578553/why-cant-the-var-keyword-in-java-be-assigned-a-lambda-expression

//		var m6 = p -> p.toString().endsWith(".txt");
//
//		System.out.println(path1 + ": " + m6.test(path1));
//		System.out.println(path2 + ": " + m6.test(path2));
//		System.out.println();
	}
}
