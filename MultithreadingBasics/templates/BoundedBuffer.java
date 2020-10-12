import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoundedBuffer<E> {

	//    0 1 2 3 4 5 6 7 8 9

	//    0 1 2 3
	//    9     4
	//    8 7 6 5

	private Object[] buffer;

	private int beg;
	private int end;

	private int num;
	private int max;

	private static Logger log = LogManager.getLogger();

	public BoundedBuffer(int bufferSize) {
		buffer = new Object[bufferSize];

		beg = 0;
		end = 0;
		num = 0;
		max = buffer.length;

		log.debug("beg: {}, end: {}, num: {}, max: {}", beg, end, num, max);
	}

	public void put(E item) throws InterruptedException {
		// TODO

		// log.debug("put(): waiting until buffer not full.");
		// log.debug("put(): woke up, checking buffer.");
		// log.debug("put(): adding {} in buffer.", item);
		// log.debug("put(): buffer now has {} elements.", num);
		// log.debug("put(): range is now ({}, {}).", beg, end);
	}

	public void putAll(E[] items) throws InterruptedException {
		// TODO
	}

	public E get() throws InterruptedException {
		// TODO
		return null;

		// log.debug("get(): waiting until buffer not empty.");
		// log.debug("get(): woke up, checking buffer.");
		// log.debug("get(): getting {} from buffer.", buffer[beg]);
		// log.debug("put(): buffer now has {} elements.", num);
		// log.debug("put(): range is now ({}, {}).", beg, end);
	}
}
