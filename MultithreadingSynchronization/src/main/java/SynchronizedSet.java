import java.util.Collection;

/**
 * A thread-safe version of {@link IndexedSet} using the synchronized keyword.
 *
 * @param <E> element type
 * @see IndexedSet
 */
public class SynchronizedSet<E> extends IndexedSet<E> {

	/**
	 * Initializes an unsorted synchronized indexed set.
	 */
	public SynchronizedSet() {
		this(false);
	}

	/**
	 * Initializes a sorted or unsorted synchronized index set depending on the
	 * parameter.
	 *
	 * @param sorted if true, will initialize a sorted set
	 */
	public SynchronizedSet(boolean sorted) {
		super(sorted);
	}

	@Override
	public synchronized boolean add(E element) {
		return super.add(element);
	}

	@Override
	public synchronized boolean addAll(Collection<E> elements) {
		return super.addAll(elements);
	}

	@Override
	public synchronized int size() {
		return super.size();
	}

	@Override
	public synchronized boolean contains(E element) {
		return super.contains(element);
	}

	@Override
	public synchronized E get(int index) {
		return super.get(index);
	}

	@Override
	public synchronized String toString() {
		return super.toString();
	}

	@Override
	public synchronized IndexedSet<E> unsortedCopy() {
		return super.unsortedCopy();
	}

	@Override
	public synchronized IndexedSet<E> sortedCopy() {
		return super.sortedCopy();
	}

	// Do not need to override copy(boolean) because it does not directly access data!

	/*
	 * Because this class uses synchronized methods instead of a private object
	 * for synchronization, it may be unsafe if the instance of this class is
	 * public (since other code will have access to the lock object and can cause
	 * a deadlock to occur). If properly used as a private member without breaking
	 * encapsulation, it should be fine.
	 */
}
