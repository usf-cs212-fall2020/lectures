import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 * An alternative to using {@link Socket} connections instead of a
 * {@link URLConnection} to fetch the headers and content from a URL on the web.
 */
public class HttpsFetcher {

	/**
	 * Fetches the headers and content for the specified URL. The content is
	 * placed as a list of all the lines fetched under the "Content" key.
	 *
	 * @param url the url to fetch
	 * @return a map with the headers and content
	 * @throws IOException if unable to fetch headers and content
	 *
	 * @see URLFetcher#fetchURL(URL)
	 */
	public static Map<String, List<String>> fetchURL(URL url) throws IOException {
		try (
				Socket socket = openConnection(url);
				PrintWriter request = new PrintWriter(socket.getOutputStream());
				InputStreamReader input = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
				BufferedReader response = new BufferedReader(input);
		) {
			printGetRequest(request, url);

			Map<String, List<String>> headers = getHeaderFields(response);
			List<String> content = getContent(response);
			headers.put("Content", content);

			return headers;
		}
	}

	/**
	 * See {@link #fetchURL(URL)} for details.
	 *
	 * @param url the url to fetch
	 * @return a map with the headers and content
	 * @throws MalformedURLException if unable to convert String to URL
	 * @throws IOException if unable to fetch headers and content
	 *
	 * @see #fetchURL(URL)
	 */
	public static Map<String, List<String>> fetchURL(String url) throws MalformedURLException, IOException {
		return fetchURL(new URL(url));
	}

	/**
	 * Uses a {@link Socket} to open a connection to the web server associated
	 * with the provided URL. Supports HTTP and HTTPS connections.
	 *
	 * @param url the url to connect
	 * @return a socket connection for that url
	 * @throws UnknownHostException if the host is not known
	 * @throws IOException if an I/O error occurs when creating the socket
	 *
	 * @see URL#openConnection()
	 */
	public static Socket openConnection(URL url) throws UnknownHostException, IOException {
		String protocol = url.getProtocol();
		String host = url.getHost();

		boolean https = protocol != null && protocol.equalsIgnoreCase("https");
		int defaultPort = https ? 443 : 80;
		int port = url.getPort() < 0 ? defaultPort : url.getPort();
		
		SocketFactory factory = https ? SSLSocketFactory.getDefault() : SocketFactory.getDefault();
		return factory.createSocket(host, port);
	}

	/**
	 * Writes a simple HTTP GET request to the provided socket writer.
	 *
	 * @param writer a writer created from a socket connection
	 * @param url the url to fetch via the socket connection
	 * @throws IOException if unable to write request to socket
	 */
	public static void printGetRequest(PrintWriter writer, URL url) throws IOException {
		String host = url.getHost();
		String resource = url.getFile().isEmpty() ? "/" : url.getFile();

		writer.printf("GET %s HTTP/1.1\r\n", resource);
		writer.printf("Host: %s\r\n", host);
		writer.printf("Connection: close\r\n");
		writer.printf("\r\n");
		writer.flush();
	}

	/**
	 * Gets the header fields from a reader associated with a socket connection.
	 * Requires that the socket reader has not yet been used, otherwise this
	 * method will return unpredictable results.
	 *
	 * @param response a reader created from a socket connection
	 * @return a map of header fields to a list of header values
	 * @throws IOException if unable to read from socket
	 *
	 * @see URLConnection#getHeaderFields()
	 */
	public static Map<String, List<String>> getHeaderFields(BufferedReader response) throws IOException {
		Map<String, List<String>> results = new HashMap<>();

		String line = response.readLine();
		results.put(null, List.of(line));

		while ((line = response.readLine()) != null && !line.isBlank()) {
			String[] split = line.split(":\\s+", 2);
			assert split.length == 2;

			results.putIfAbsent(split[0], new ArrayList<>());
			results.get(split[0]).add(split[1]);
		}

		return results;
	}

	/**
	 * Gets the content from a socket. Whether this output includes headers
	 * depends how the socket connection has already been used.
	 *
	 * @param response the reader created from a socket connection
	 * @return a list of lines read from the socket reader
	 * @throws IOException if unable to read from socket
	 */
	public static List<String> getContent(BufferedReader response) throws IOException {
		return response.lines().collect(Collectors.toList());
	}

	/**
	 * Demonstrates the {@link #fetchURL(URL)} method.
	 *
	 * @param args unused
	 * @throws Exception if unable to fetch url
	 */
	public static void main(String[] args) throws Exception {
		String[] urls = new String[] { 
				"http://www.cs.usfca.edu/", // 302 -> https
				"https://www.cs.usfca.edu/", // 302 -> myusf
				"https://www.cs.usfca.edu/~cs212/", // 202
				"https://www.cs.usfca.edu/~cs212/simple/double_extension.html.txt", // text/plain
				"https://www.cs.usfca.edu/~cs212/nowhere.html", // 404
				"https://www.cs.usfca.edu/~cs212/redirect/gone", "https://www.cs.usfca.edu/~cs212/redirect/loop1",
				"https://www.cs.usfca.edu/~cs212/redirect/one" };

		for (String url : urls) {
			System.out.println(url);
			System.out.println(fetchURL(url));
			System.out.println();
		}
	}
}
