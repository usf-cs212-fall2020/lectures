import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringSubstitutor;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/**
 * Demonstrates how to create, use, and clear cookies.
 *
 * @see CookieVisitServlet
 * @see CookieLandingServlet
 */
public class CookieLandingServlet extends HttpServlet {
	/** Class version for serialization, in [YEAR][TERM] format (unused). */
	private static final long serialVersionUID = 202040;

	/** Log used by Jetty (not log4j2). */
	private static final Logger log = Log.getRootLogger();

	/** The title to use for this webpage. */
	private static final String TITLE = "Cookies!";

	/** Location of the HTML template for this servlet. */
	private static final Path TEMPLATE_PATH = Path.of("html", "cookie_landing.html");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("GET " + request.getRequestURL().toString());

		Map<String, String> values = new HashMap<>();

		// configure the page info
		values.put("title", TITLE);
		values.put("url", request.getRequestURL().toString());
		values.put("path", request.getRequestURI());
		values.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		values.put("thread", Thread.currentThread().getName());

		// configure the form info
		values.put("method", "POST");
		values.put("action", "/visits");

		// generate html
		String template = Files.readString(TEMPLATE_PATH, StandardCharsets.UTF_8);
		StringSubstitutor replacer = new StringSubstitutor(values);

		// output html
		PrintWriter out = response.getWriter();
		out.write(replacer.replace(template));

		// finish up response
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}
}
