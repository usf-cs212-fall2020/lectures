import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class PathMatcherDemo {

	public static void main(String[] args) {
		Path path1 = Path.of("hello.txt");
		Path path2 = Path.of("txt.hello");

		PathMatcher m1 = new PathMatcher() {
			@Override
			public boolean matches(Path p) {
				return p.toString().endsWith(".txt");
			}
		};

		System.out.println(path1 + ": " + m1.matches(path1));
		System.out.println(path2 + ": " + m1.matches(path2));
		System.out.println();

		// TODO
	}
}
