import java.util.Arrays;
import java.util.TreeSet;

public class QuizDemo {

	public static void instancesQuestions() {
		String a = "rutabaga";
		String b = a;
		String c = new String(a);

		System.out.println(System.identityHashCode(a));
		System.out.println(System.identityHashCode(b));
		System.out.println(System.identityHashCode(c));
	}

	public static class Widget {
		private String a;
		private static String b;

		@SuppressWarnings("static-access")
		public static void main(String[] args) {
			Widget one = new Widget();
			Widget two = new Widget();

			one.a = "hello";
			one.b = "world";

			two.a = "good night";
			two.b = "good luck";

			System.out.println(one.a + " " + one.b); // Line 1
			System.out.println(two.a + " " + two.b); // Line 2
		}
	}

	private static String text;

	public static void testMutability1(String text) {
		text.concat("apple");
	}

	public static void testMutability2() {
		text += "cone";
	}

	public static void testMutability1(StringBuilder text) {
		text.append("apple");
	}

	public static void testMutability2(StringBuilder text) {
		text = new StringBuilder("pinecone");
	}

	public static void mutability1Questions() {
		text = "pine";
		testMutability2();
		System.out.println(text);

		String text1 = new String("pine");
		testMutability1(text1);
		System.out.println(text1);

		StringBuilder text2 = new StringBuilder("pine");
		testMutability1(text2);
		System.out.println(text2);

		StringBuilder text3 = new StringBuilder("pine");
		testMutability2(text3);
		System.out.println(text3);
	}

	public static void mutability2Questions() {
		int[] a = new int[] { 1, 2, 3 };
		int[] b = a;
		b[0] = 4;
		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(b));

		int c = 42;
		int d = c;
		d = 24;
		System.out.println(c);
		System.out.println(d);

		String g = new String("dragon");
		String h = g;
		h += "fly";
		System.out.println(g);
		System.out.println(h);

		StringBuilder k = new StringBuilder("dragon");
		StringBuilder l = new StringBuilder(k);
		l.append("fly");
		System.out.println(k);
		System.out.println(l);

		TreeSet<String> m = new TreeSet<>();
		m.add("ant");

		TreeSet<String> n = m;
		n.add("bat");
		n.add("cat");

		System.out.println(m);
		System.out.println(n);
	}

	public static void main(String[] args) {
		instancesQuestions();
		Widget.main(args);
		mutability1Questions();
		mutability2Questions();
	}

}
