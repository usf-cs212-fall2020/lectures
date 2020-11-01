import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class MessageServlet extends HttpServlet {

	private static final long serialVersionUID = 202040;
	private static final String TITLE = "Messages";

	private static Logger log = Log.getRootLogger();

	private final ConcurrentLinkedQueue<String> messages;
	private final String htmlTemplate;

	public MessageServlet() throws IOException {
		super();
		messages = new ConcurrentLinkedQueue<>();
		htmlTemplate = Files.readString(Path.of("html", "index.html"), StandardCharsets.UTF_8);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		log.info("MessageServlet ID " + this.hashCode() + " handling GET request.");

		PrintWriter out = response.getWriter();

		// TODO
		out.println(TITLE);
		out.println(messages);
		out.println(htmlTemplate);

		out.flush();
		response.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		log.info("MessageServlet ID " + this.hashCode() + " handling POST request.");

		// TODO
		// String format = "<p>%s<br><font size=\"-2\">[ posted by %s at %s ]</font></p>";

		response.setStatus(HttpServletResponse.SC_OK);
	}

	public static String getDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}
}
