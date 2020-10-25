import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLFetcher {

	// https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/Identifying_resources_on_the_Web#Syntax_of_Uniform_Resource_Identifiers_(URIs)
	// https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
	// https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers
	// https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Connection

	public static final String CONTENT = "Content";

	public static Map<String, List<String>> fetchURL(URL url) throws IOException {
		Map<String, List<String>> results = new HashMap<>();

		// TODO

		return results;
	}

	public static Map<String, List<String>> fetchURL(String url) throws MalformedURLException, IOException {
		return fetchURL(new URL(url));
	}

	public static void main(String[] args) throws Exception {
		String url = "http://www.cs.usfca.edu/";

		var results = fetchURL(url);

		System.out.println(url);

		for (var entry : results.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		System.out.println();
	}

	// "http://www.cs.usfca.edu/"
	// "https://www.cs.usfca.edu/"
	// "https://www.cs.usfca.edu/~cs212/"
	// "https://www.cs.usfca.edu/~cs212/simple/double_extension.html.txt"
	// "https://www.cs.usfca.edu/~cs212/nowhere.html"
}
