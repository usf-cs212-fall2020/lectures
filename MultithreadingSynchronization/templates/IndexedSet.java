import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class IndexedSet<E> {

	private final Set<E> set;

	public IndexedSet() {
		this(false);
	}

	public IndexedSet(boolean sorted) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public boolean add(E element) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public boolean addAll(Collection<E> elements) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public int size() {
		return set.size();
	}

	public boolean contains(E element) {
		return set.contains(element);
	}

	public E get(int index) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public String toString() {
		return set.toString();
	}

	public IndexedSet<E> unsortedCopy() {
		return null; // TODO
	}

	public IndexedSet<E> sortedCopy() {
		return null; // TODO
	}

	public IndexedSet<E> copy(boolean sorted) {
		return sorted ? sortedCopy() : unsortedCopy();
	}

	public static void main(String[] args) {
		ArrayList<String> elements = new ArrayList<>();
		Collections.addAll(elements, "ant", "fox", "fly", "bee");

		IndexedSet<String> sorted = new IndexedSet<>(true);
		IndexedSet<String> unsorted = new IndexedSet<>();

		sorted.addAll(elements);
		unsorted.addAll(elements);

		// TODO
	}
}
