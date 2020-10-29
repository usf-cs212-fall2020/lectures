import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * A simple example of using Jetty and servlets to create a dynamic web page.
 * The web page will display the current date/time when loaded.
 */
public class TodayServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/**
	 * Sets up a Jetty server with explicit connector and handler components.
	 *
	 * @param args unused
	 * @throws Exception if unable to start web server
	 */
	public static void main(String[] args) throws Exception {
		// Create the Jetty server
		Server server = new Server();

		// Setup the connector component
		ServerConnector connector = new ServerConnector(server);
		connector.setHost("localhost");
		connector.setPort(PORT);

		// Setup the handler component
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(TodayServlet.class, "/today");

		// Configure server to use connector and handler
		server.addConnector(connector);
		server.setHandler(handler);

		// Start the server (it is a thread)
		server.start();
		System.out.println(" Server: " + server.getState());
		System.out.println("Threads: " + server.getThreadPool().getThreads());

		// Keeps main thread active as long as server is active
		// Until we implement shutdown, the server will never terminate
		server.join();
		System.out.println(" Server: " + server.getState());
	}

	/**
	 * A simple servlet that will display the current date and time when loaded.
	 */
	public static class TodayServlet extends HttpServlet {
		/** Class version for serialization, in [YEAR][TERM] format (unused). */
		private static final long serialVersionUID = 202040;

		/** Title of web page. */
		private static final String TITLE = "Today";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// Output request for debugging (better if use logger)
			System.out.println(Thread.currentThread().getName() + ": " + request.getRequestURI());

			PrintWriter out = response.getWriter();
			response.setContentType("text/html");

			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			out.printf("<p>It is %s.</p>%n", getDate());

			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

	/**
	 * Returns the date and time in a long format. For example: "12:00 am on
	 * Saturday, January 01 2000".
	 *
	 * @return current date and time
	 */
	public static String getDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}
}
