import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demonstrates using a {@link URLConnection} to fetch the headers and content
 * from a URL on the web.
 */
public class URLFetcher {

	// https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers

	/**
	 * Fetches the headers and content for the specified URL. The content is
	 * placed as a list of all the lines fetched under the "Content" key.
	 *
	 * @param url the url to fetch
	 * @return a map with the headers and content
	 * @throws IOException if unable to fetch headers and content
	 */
	public static Map<String, List<String>> fetchURL(URL url) throws IOException {
		// used to store all headers and content
		Map<String, List<String>> results = new HashMap<>();

		// connection to url
		URLConnection urlConnection = url.openConnection();

		// by default HttpURLConnection will follow redirects (within same protocol)
		// automatically
		// this is the connection usually underlying URLConnection
		HttpURLConnection.setFollowRedirects(false);

		// close connection instead of keep-alive
		urlConnection.setRequestProperty("Connection", "close");

		// get all of the headers
		// note the status code will be placed with a "null" key
		results.putAll(urlConnection.getHeaderFields());

		try (
				InputStreamReader input = new InputStreamReader(urlConnection.getInputStream());
				BufferedReader reader = new BufferedReader(input);
				Stream<String> stream = reader.lines();
		) {
			List<String> lines = stream.collect(Collectors.toList());
			results.put("Content", lines);
		}
		catch (IOException e) {
			results.put("Content", Collections.emptyList());
		}

		return results;
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
	 * Demonstrates the {@link #fetchURL(URL)} method.
	 *
	 * @param args unused
	 * @throws Exception if unable to fetch url
	 */
	public static void main(String[] args) throws Exception {
		String[] urls = new String[] { 
				"http://www.cs.usfca.edu/", // 302 -> https
				"https://www.cs.usfca.edu/", // 302 -> myusf
				"https://www.cs.usfca.edu/~cs212/", // 200
				"https://www.cs.usfca.edu/~cs212/simple/double_extension.html.txt", // text/plain
				"https://www.cs.usfca.edu/~cs212/nowhere.html" // 404
		};

		for (String url : urls) {
			var results = fetchURL(url);

			System.out.println(url);

			for (var entry : results.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}

			System.out.println();
		}
	}
}
