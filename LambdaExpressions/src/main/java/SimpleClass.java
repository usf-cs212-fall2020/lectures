/**
 * A simple class that implements a functional interface.
 *
 * @see SimpleInterface
 * @see LambdaDemo
 */
public class SimpleClass implements SimpleInterface {

	@Override
	public void simpleMethod() {
		// Using getTypeName() because it provides output even with anonymous
		// classes and lambda expressions
		System.out.println(this.getClass().getTypeName());
	}

}
