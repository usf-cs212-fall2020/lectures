import java.util.LinkedList;

public class WorkQueue {

	private final PoolWorker[] workers;
	private final LinkedList<Runnable> queue;

	private volatile boolean shutdown;

	public static final int DEFAULT = 5;

	public WorkQueue() {
		this(DEFAULT);
	}

	public WorkQueue(int threads) {
		this.queue = new LinkedList<Runnable>();
		this.workers = new PoolWorker[threads];

		shutdown = false;

		// TODO
		System.out.println(queue);
		System.out.println(shutdown);
	}

	public void execute(Runnable task) {
		// TODO
	}

	public void shutdown() {
		// TODO
	}

	public int size() {
		return workers.length;
	}

	private class PoolWorker extends Thread {

		@Override
		public void run() {
			Runnable task = null;

			while (true) {
				// TODO
				System.out.println(task);
			}
		}
	}
}
