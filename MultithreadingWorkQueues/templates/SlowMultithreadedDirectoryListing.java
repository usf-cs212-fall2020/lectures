import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SlowMultithreadedDirectoryListing {

	private static final Logger log = LogManager.getLogger();

	public static Set<Path> list(Path path) throws InterruptedException {
		HashSet<Path> paths = new HashSet<>();

		if (Files.exists(path)) {
			paths.add(path);

			if (Files.isDirectory(path)) {
				// TODO
				new Worker(path);
			}
		}

		return paths;
	}

	private static class Worker extends Thread {

		private final Path path;

		public Worker(Path path) { // TODO
			this.path = path;

			// TODO
			log.debug("Worker for {} created.", path);
		}

		@Override
		public void run() {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path current : stream) {
					// TODO
					System.out.println(current);
				}
			}
			catch (IOException ex) {
				// TODO
			}
			/*
			 * TODO catch (InterruptedException ex) {
			 *
			 * }
			 */

			log.debug("Worker for {} finished.", path);
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Path path = Path.of(".");
		Set<Path> actual = list(path);
		Set<Path> expected = RecursiveDirectoryListing.list(path);

		System.out.println(actual.equals(expected));
	}
}
