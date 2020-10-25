import java.io.Closeable;

public class SimpleServer {
	public static final int PORT = 5554;
	public static final String EOT = "EOT";
	public static final String EXIT = "SHUTDOWN";

	public static void main(String[] args) throws Exception {
		String line = "";

		try (
				// TODO
				Closeable placeholder1 = () -> { /* TODO: DELETE ME */ };
		) {

			/* while ( TODO ) { */
				System.out.println("Server: Waiting for connection...");

				try (
						// TODO
						Closeable placeholder2 = () -> { /* TODO: DELETE ME */ };
				) {
					/* while ( TODO ) { */
						// TODO
						System.out.println("Server: " + line);

						if (line.equals(EOT)) {
							System.out.println("Server: Closing socket.");
							// TODO
						}
						else if (line.equals(EXIT)) {
							System.out.println("Server: Shutting down.");
							// TODO
						}
					// } TODO
				}

				System.out.println("Server: Client disconnected.");
			// } TODO
		}
	}
}
