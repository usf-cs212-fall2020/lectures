import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class CookieServer {
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
