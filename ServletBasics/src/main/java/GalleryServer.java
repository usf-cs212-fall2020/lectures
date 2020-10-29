import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Demonstrates one way to serve both dynamic and static resources. Requires a
 * local web/foto directory with several jpg images to function properly.
 */
public class GalleryServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/**
	 * Sets up a Jetty server with a resource, context, and servlet context
	 * handler to respond to requests for both dynamic and static resources.
	 *
	 * @param args unused
	 * @throws Exception if unable to start and run server
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		// add static resource holders to web server
		// this indicates where web files are accessible on the file system
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("foto");
		resourceHandler.setDirectoriesListed(true);

		// only serve static resources in the "/images" context directory
		// this indicates where web files are accessible via the web server
		ContextHandler resourceContext = new ContextHandler("/images");
		resourceContext.setHandler(resourceHandler);

		// all other requests should be handled by the gallery servlet
		ServletContextHandler servletContext = new ServletContextHandler();
		servletContext.setContextPath("/");
		servletContext.addServlet(GalleryServlet.class, "/");

		// setup handlers (and handler order)
		HandlerList handlers = new HandlerList();
		handlers.addHandler(resourceContext);
		handlers.addHandler(servletContext);

		// order matters---if you swap the order, we no longer see the individual
		// files listed

		server.setHandler(handlers);
		server.start();
		server.join();

		// http://localhost:8080/
		// http://localhost:8080/nowhere
		// http://localhost:8080/images
	}

	/**
	 * Automatically adds all of the images found in the web/foto subdirectory to
	 * a gallery page.
	 */
	public static class GalleryServlet extends HttpServlet {
		/** Class version for serialization, in [YEAR][TERM] format (unused). */
		private static final long serialVersionUID = 202040;

		/** The title to use for this webpage. */
		private static final String TITLE = "Gallery";

		/** The location of the images to process. */
		private static final Path ROOT = Path.of("foto");

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			// loop through the local directory and get all the jpg files
			try (DirectoryStream<Path> dir = Files.newDirectoryStream(ROOT, p -> p.toString().endsWith(".jpg"));) {
				// for each returned file, add to gallery page
				// assumes photos will be served out of the images/ directory
				for (Path file : dir) {
					out.printf("<img src=\"/images/%s\" width=\"150\" height=\"150\">%n", file.getFileName().toString());
				}
			}

			out.printf("<p>This request was handled by thread %s.</p>%n", Thread.currentThread().getName());
			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
