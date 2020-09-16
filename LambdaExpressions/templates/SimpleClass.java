public class SimpleClass implements SimpleInterface {

	@Override
	public void simpleMethod() {
		System.out.println(this.getClass().getTypeName());
	}

}
