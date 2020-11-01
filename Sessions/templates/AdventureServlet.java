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

public class AdventureServlet extends HttpServlet {
	public static final long serialVersionUID = 202040;
	public static Logger log = Log.getRootLogger();
	public static final String TITLE = "Adventure!";
	public static final Path TEMPLATE_PATH = Path.of("html", "adventure.html");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> values = new HashMap<>();

		values.put("title", TITLE);
		values.put("url", request.getRequestURL().toString());
		values.put("path", request.getRequestURI());
		values.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		values.put("thread", Thread.currentThread().getName());

		// TODO

		String template = Files.readString(TEMPLATE_PATH, StandardCharsets.UTF_8);
		StringSubstitutor replacer = new StringSubstitutor(values);
		String html = replacer.replace(template);

		PrintWriter writer = response.getWriter();
		writer.write(html);

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
