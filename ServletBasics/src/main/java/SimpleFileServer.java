import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * A simple example of using Jetty and servlets to both serve static resources
 * and dynamic content.
 */
public class SimpleFileServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/**
	 * Sets up a Jetty server with both a resource and servlet handler. Able to
	 * respond with static and dynamic content.
	 *
	 * @param args unused
	 * @throws Exception if unable to start server
	 */
	public static void main(String[] args) throws Exception {
		// Enable DEBUG logging
		System.setProperty("org.eclipse.jetty.LEVEL", "DEBUG");

		Server server = new Server(PORT);

		// Add static resource holders to web server
		// This indicates where web files are accessible on the file system
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);

		// Try both .setResourceBase(".") and .setResourceBase("./src")
		resourceHandler.setResourceBase(".");

		// Can still assign servlets to specific requests
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(VisitServer.VisitServlet.class, "/visits");

		// Setup handlers (and handler order)
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resourceHandler, servletHandler });

		server.setHandler(handlers);
		server.start();
		server.join();

		// http://localhost:8080/
		// http://localhost:8080/visits
		// http://localhost:8080/src/
	}
}
