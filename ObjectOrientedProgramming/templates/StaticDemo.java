public class StaticDemo {

	private int a;
	private static int b;

	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		StaticDemo d1 = new StaticDemo();
		d1.a = 0;
		d1.b = 1;

		System.out.println("d1.a = " + d1.a + ", d1.b = " + d1.b);

//		System.out.println("d2.a = " + d2.a + ", d2.b = " + d2.b);
//		System.out.println();

//		System.out.println("d1.a = " + d1.a + ", d1.b = " + d1.b);
//		System.out.println();

//		System.out.println("d1.a = " + d1.a + ", d1.b = " + d1.b);
//		System.out.println("d2.a = " + d2.a + ", d2.b = " + d2.b);
	}
}
