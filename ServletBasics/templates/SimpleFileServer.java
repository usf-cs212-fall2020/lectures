import org.eclipse.jetty.server.Server;

public class SimpleFileServer {
  public static final int PORT = 8080;

  public static void main(String[] args) throws Exception {
    // TODO Enable DEBUG logging

    Server server = new Server(PORT);

    // TODO Setup file server

    server.start();
    server.join();
  }
}
