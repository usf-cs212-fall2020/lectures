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
import org.apache.commons.text.StringSubstitutor;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/**
 * An alternative implemention of the {@MessageServlet} class but using the
 * Bulma CSS framework.
 *
 * @see MessageServlet
 */
public class BulmaMessageServlet extends HttpServlet {

	/** Class version for serialization, in [YEAR][TERM] format (unused). */
	private static final long serialVersionUID = 202040;

	/** The title to use for this webpage. */
	private static final String TITLE = "Fancy Messages";

	/** The logger to use for this servlet. */
	private static Logger log = Log.getRootLogger();

	/** The thread-safe data structure to use for storing messages. */
	private final ConcurrentLinkedQueue<String> messages;

	/** Template for starting HTML (including <head> tag). **/
	private final String headTemplate;

	/** Template for ending HTML (including <foot> tag). **/
	private final String footTemplate;

	/** Template for individual message HTML. **/
	private final String textTemplate;

	/**
	 * Initializes this message board. Each message board has its own collection
	 * of messages.
	 * 
	 * @throws IOException if unable to read templates
	 */
	public BulmaMessageServlet() throws IOException {
		super();
		messages = new ConcurrentLinkedQueue<>();

		// load templates
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

		// generate html from template
		StringSubstitutor replacer = new StringSubstitutor(values);
		String head = replacer.replace(headTemplate);
		String foot = replacer.replace(footTemplate);

		// output generated html
		PrintWriter out = response.getWriter();
		out.println(head);

		if (messages.isEmpty()) {
			out.printf("    <p>No messages.</p>%n");
		}
		else {
			// could be multiple threads, but the data structure handles
			// synchronization
			for (String message : messages) {
				out.println(message);
			}
		}

		out.println(foot);
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

		// avoid xss attacks using apache commons text
		// comment out if you don't have this library installed
		name = StringEscapeUtils.escapeHtml4(name);
		message = StringEscapeUtils.escapeHtml4(message);

		// used to substitute values in our templates
		Map<String, String> values = new HashMap<>();
		values.put("name", name);
		values.put("message", message);
		values.put("timestamp", getDate());

		// generate html from template
		StringSubstitutor replacer = new StringSubstitutor(values);
		String formatted = replacer.replace(textTemplate);

		// keep in mind multiple threads may access at once
		// but we are using a thread-safe data structure here to avoid any issues
		messages.add(formatted);

		// only keep the latest 5 messages
		if (messages.size() > 5) {
			String first = messages.poll();
			log.info("Removing message: " + first);
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(request.getServletPath());
	}

	/**
	 * Returns the date and time in a long format. For example: "12:00 am on
	 * Saturday, January 01 2000".
	 *
	 * @return current date and time
	 */
	private static String getDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}
}
