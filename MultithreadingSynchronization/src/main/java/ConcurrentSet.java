import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A thread-safe version of {@link IndexedSet} using a read/write lock.
 *
 * @param <E> element type
 * @see IndexedSet
 * @see ReadWriteLock
 * @see ReentrantReadWriteLock
 */
public class ConcurrentSet<E> extends IndexedSet<E> {

	/** The lock object to use. */
	private ReentrantReadWriteLock lock;

	/**
	 * Initializes an unsorted synchronized indexed set.
	 */
	public ConcurrentSet() {
		this(false);
	}

	/**
	 * Initializes a sorted or unsorted synchronized index set depending on the
	 * parameter.
	 *
	 * @param sorted if true, will initialize a sorted set
	 */
	public ConcurrentSet(boolean sorted) {
		super(sorted);

		lock = new ReentrantReadWriteLock();
	}

	@Override
	public boolean add(E element) {
		lock.writeLock().lock();

		try {
			return super.add(element);
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public boolean addAll(Collection<E> elements) {
		lock.writeLock().lock();

		try {
			return super.addAll(elements);
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public int size() {
		lock.readLock().lock();

		try {
			return super.size();
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public boolean contains(E element) {
		lock.readLock().lock();

		try {
			return super.contains(element);
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public E get(int index) {
		lock.readLock().lock();

		try {
			return super.get(index);
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public String toString() {
		lock.readLock().lock();

		try {
			return super.toString();
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public IndexedSet<E> unsortedCopy() {
		lock.readLock().lock();

		try {
			return super.unsortedCopy();
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public IndexedSet<E> sortedCopy() {
		lock.readLock().lock();

		try {
			return super.sortedCopy();
		}
		finally {
			lock.readLock().unlock();
		}
	}
}
