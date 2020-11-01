import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * Demonstrates how to create, use, and clear cookies. Uses
 * {@link StringEscapeUtils} to sanitize data fetched from cookies.
 *
 * <p>
 * Read about the "cookie law" on getting consent to store cookies at:
 * <a href="https://gdpr.eu/cookies/">https://gdpr.eu/cookies</a>
 *
 * @see CookieVisitServlet
 * @see CookieLandingServlet
 */
public class CookieServer {
	/**
	 * Starts a Jetty server configured to use the cookie index and config
	 * servlets.
	 *
	 * @param args unused
	 * @throws Exception if unable to start or run server
	 */
	public static void main(String[] args) throws Exception {
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(CookieLandingServlet.class, "/");
		handler.addServletWithMapping(CookieVisitServlet.class, "/visits");

		Server server = new Server(8080);
		server.setHandler(handler);
		server.start();
		server.join();
	}
}
