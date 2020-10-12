import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * A customized set class with terrible efficiency that allows access by index,
 * and supports sorted or unsorted ordering. (You would never want to use this
 * implementation---see {@link LinkedHashSet} instead.)
 *
 * @param <E> element type sorted in set
 */
public class IndexedSet<E> {

	/** Set of elements */
	private final Set<E> set;

	/**
	 * Initializes an unsorted set.
	 *
	 * @see #IndexedSet(boolean)
	 */
	public IndexedSet() {
		this(false);
	}

	/**
	 * Initializes a sorted or unsorted set depending on the parameter.
	 *
	 * @param sorted if true, will initialize a sorted set
	 */
	public IndexedSet(boolean sorted) {
		if (sorted) {
			set = new TreeSet<E>();
		}
		else {
			set = new HashSet<E>();
		}
	}

	/**
	 * Adds an element to our set.
	 *
	 * @param element element to add
	 * @return true if the element was added (false if it was a duplicate)
	 *
	 * @see Set#add(Object)
	 */
	public boolean add(E element) {
		return set.add(element);
	}

	/**
	 * Adds the collection of elements to our set.
	 *
	 * @param elements elements to add
	 * @return true if any elements were added (false if were all duplicates)
	 *
	 * @see Set#addAll(Collection)
	 */
	public boolean addAll(Collection<E> elements) {
		return set.addAll(elements);
	}

	/**
	 * Returns the number of elements in our set.
	 *
	 * @return number of elements
	 *
	 * @see Set#size()
	 */
	public int size() {
		return set.size();
	}

	/**
	 * Returns whether the element is contained in our set.
	 *
	 * @param element element to search for
	 * @return true if the element is contained in our set
	 *
	 * @see Set#contains(Object)
	 */
	public boolean contains(E element) {
		return set.contains(element);
	}

	/**
	 * Gets the element at the specified index based on iteration order. The
	 * element at this index may change over time as new elements are added.
	 *
	 * @param index index of element to get
	 * @return element at the specified index or null of the index was invalid
	 */
	public E get(int index) {
		if (index < 0 || index >= set.size()) {
			throw new IndexOutOfBoundsException(index);
		}

		return set.stream().skip(index).findFirst().get();
	}

	@Override
	public String toString() {
		return set.toString();
	}

	/**
	 * Returns an unsorted copy of this set.
	 *
	 * @return unsorted copy
	 *
	 * @see HashSet#HashSet(Collection)
	 */
	public IndexedSet<E> unsortedCopy() {
		IndexedSet<E> copy = new IndexedSet<>(false);
		copy.addAll(set);
		return copy;
	}

	/**
	 * Returns a sorted copy of this set.
	 *
	 * @return sorted copy
	 *
	 * @see TreeSet#TreeSet(Collection)
	 */
	public IndexedSet<E> sortedCopy() {
		IndexedSet<E> copy = new IndexedSet<>(true);
		copy.addAll(set);
		return copy;
	}

	/**
	 * Returns a copy of the indexed set.
	 *
	 * @param sorted controls whether the copy is sorted or unsorted
	 * @return a sorted copy of the indexed set if the parameter is true,
	 *         otherwise an unsorted copy of the indexed set
	 */
	public IndexedSet<E> copy(boolean sorted) {
		return sorted ? sortedCopy() : unsortedCopy();
	}

	/**
	 * Demonstrates the basic functionality of this class. Used for testing.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		ArrayList<String> elements = new ArrayList<>();
		Collections.addAll(elements, "ant", "fox", "fly", "bee");

		IndexedSet<String> sorted = new IndexedSet<>(true);
		IndexedSet<String> unsorted = new IndexedSet<>();

		sorted.addAll(elements);
		unsorted.addAll(elements);

		System.out.println(sorted);
		System.out.println(sorted.unsortedCopy());

		System.out.println(unsorted);
		System.out.println(unsorted.sortedCopy());

		System.out.println(sorted.get(0));
		System.out.println(unsorted.get(0));

		System.out.println(sorted.get(3));
		System.out.println(unsorted.get(3));

		System.out.println(sorted.get(10));
		System.out.println(unsorted.get(10));

	}

	/*
	 * There really isn't a good reason to use this class... the get() method is
	 * very inefficient. See LinkedHashSet for a better alternative.
	 *
	 * But, it helps us illustrate how to make a class like this thread-safe.
	 */
}
