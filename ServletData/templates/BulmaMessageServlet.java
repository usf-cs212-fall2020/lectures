import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class BulmaMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 202040;
	private static final String TITLE = "Fancy Messages";
	private static Logger log = Log.getRootLogger();

	private final ConcurrentLinkedQueue<String> messages;

	private final String headTemplate;
	private final String footTemplate;
	private final String textTemplate;

	public BulmaMessageServlet() throws IOException {
		super();
		messages = new ConcurrentLinkedQueue<>();

		headTemplate = Files.readString(Path.of("html", "bulma-head.html"), StandardCharsets.UTF_8);
		footTemplate = Files.readString(Path.of("html", "bulma-foot.html"), StandardCharsets.UTF_8);
		textTemplate = Files.readString(Path.of("html", "bulma-text.html"), StandardCharsets.UTF_8);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		log.info("MessageServlet ID " + this.hashCode() + " handling GET request.");

		// used to substitute values in our templates
		Map<String, String> values = new HashMap<>();
		values.put("title", TITLE);
		values.put("thread", Thread.currentThread().getName());
		values.put("updated", getDate());

		// setup form
		values.put("method", "POST");
		values.put("action", request.getServletPath());

		PrintWriter out = response.getWriter();

		// TODO
		out.println(headTemplate);
		out.println(textTemplate);
		out.println(footTemplate);

		out.flush();
		response.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		log.info("MessageServlet ID " + this.hashCode() + " handling POST request.");

		String name = request.getParameter("name");
		String message = request.getParameter("message");

		name = name == null ? "anonymous" : name;
		message = message == null ? "" : message;

		name = StringEscapeUtils.escapeHtml4(name);
		message = StringEscapeUtils.escapeHtml4(message);

		// TODO

		// only keep the latest 5 messages
		if (messages.size() > 5) {
			String first = messages.poll();
			log.info("Removing message: " + first);
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(request.getServletPath());
	}

	public static String getDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}
}
