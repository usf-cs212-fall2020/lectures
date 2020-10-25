import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpsFetcher extends URLFetcher {

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
