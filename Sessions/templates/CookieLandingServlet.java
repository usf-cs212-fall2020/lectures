import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class CookieLandingServlet extends HttpServlet {
	public static final long serialVersionUID = 202040;
	public static final Logger log = Log.getRootLogger();
	public static final String TITLE = "Cookies!";
	public static final Path TEMPLATE_PATH = Path.of("html", "cookie_landing.html");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("GET " + request.getRequestURL().toString());

		Map<String, String> values = new HashMap<>();

		values.put("title", TITLE);
		values.put("url", request.getRequestURL().toString());
		values.put("path", request.getRequestURI());
		values.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		values.put("thread", Thread.currentThread().getName());

		// TODO

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}
}
