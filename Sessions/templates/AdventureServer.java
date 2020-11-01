import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;

public class AdventureServer {
	public static void main(String[] args) throws Exception {
		// TODO

		HandlerList handlers = new HandlerList();
		// TODO

		Server server = new Server(8080);
		server.setHandler(handlers);
		server.start();
		server.join();
	}
}
