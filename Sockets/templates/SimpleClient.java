import java.io.Closeable;

public class SimpleClient {

	public static void main(String[] args) throws Exception {
		try (
			// TODO
			Closeable placeholder = () -> { /* TODO: DELETE ME */ };
		){

			System.out.println("Client: Started...");
			String input = "";

			/* while ( TODO ) { */
				// TODO

				if (input.equals(SimpleServer.EOT)) {
					System.out.println("Client: Ending client.");
					// TODO
				}
				else if (input.equals(SimpleServer.EXIT)) {
					System.out.println("Client: Shutting down server.");
					// TODO
				}
			// } TODO
		}
	}
}
