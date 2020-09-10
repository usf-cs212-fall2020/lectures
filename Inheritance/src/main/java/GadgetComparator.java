import java.util.Comparator;

/**
 * This class implements {@link Comparator} to change how {@link Gadget} objects
 * are sorted.
 */
public class GadgetComparator implements Comparator<Gadget> {

	/**
	 * Will sort {@link Gadget} objects by the length of the name instead of the
	 * id.
	 */
	@Override
	public int compare(Gadget one, Gadget two) {
		int length1 = one.name.length();
		int length2 = two.name.length();

		// Use Integer.compare(length2, length1) to swap order.
		return Integer.compare(length1, length2);
	}
}
