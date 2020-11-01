import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/**
 * Demonstrates how to create, use, and clear cookies.
 *
 * @see CookieVisitServlet
 * @see CookieLandingServlet
 */
public class CookieVisitServlet extends HttpServlet {
	/** Class version for serialization, in [YEAR][TERM] format (unused). */
	private static final long serialVersionUID = 202040;

	/** Log used by Jetty (not log4j2). */
	private static final Logger log = Log.getRootLogger();

	/** The title to use for this webpage. */
	private static final String TITLE = "Cookies!";

	/** Used to fetch whether cookies were approved. */
	private static final String COOKIES_OK = "Cookies";

	/** Used to fetch the visited date from a cookie. */
	private static final String VISIT_DATE = "Visited";

	/** Used to fetch the visited count from a cookie. */
	private static final String VISIT_COUNT = "Count";

	/** Used to format date/time output. */
	private static final String DATE_FORMAT = "hh:mm a 'on' EEEE, MMMM dd yyyy";

	/** Location of the HTML template for this servlet. */
	private static final Path TEMPLATE_PATH = Path.of("html", "cookie_visits.html");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("GET " + request.getRequestURL().toString());

		// skip requests for favicon.ico
		if (request.getRequestURI().endsWith("favicon.ico")) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// create map for html template
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

		// put default values for count and visit date/time
		values.put("visit_count", "<em>unknown</em>");
		values.put("visit_date", "<em>unknown</em>");

		// put default values for enabled/disabled buttons
		values.put("enable_on", "");
		values.put("disable_on", "");

		// get all existing cookies
		Map<String, Cookie> cookies = getCookieMap(request);
		values.put("cookies", Integer.toString(cookies.size()));

		// check if we are allowed to use cookies
		if (cookies.containsKey(COOKIES_OK)) {
			log.info("Cookies are enabled.");
			values.put("enable_on", "disabled");

			// set initial count and date
			int count = 1;

			// check for existing count
			if (cookies.containsKey(VISIT_COUNT)) {
				try {
					Cookie visitCount = cookies.get(VISIT_COUNT);
					count = Integer.parseInt(visitCount.getValue()) + 1;
				}
				catch (NumberFormatException e) {
					log.ignore(e);
				}
			}

			// update our html template and cookie
			values.put("visit_count", Integer.toString(count));
			response.addCookie(new Cookie(VISIT_COUNT, Integer.toString(count)));

			// set current date and time
			String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

			// check for existing date
			if (cookies.containsKey(VISIT_DATE)) {
				Cookie visitDate = cookies.get(VISIT_DATE);
				String visited = visitDate.getValue();

				// do not trust values stored in cookies either!
				String decoded = URLDecoder.decode(visited, StandardCharsets.UTF_8);
				String escaped = StringEscapeUtils.escapeHtml4(decoded);

				// update our html template
				values.put("visit_date", escaped);
			}
			else {
				values.put("visit_date", "N/A (first visit)");
			}

			// update visit date, must URL encode
			String encodedDate = URLEncoder.encode(today, StandardCharsets.UTF_8);
			response.addCookie(new Cookie(VISIT_DATE, encodedDate));
		}
		else {
			log.info("Cookies are disabled.");
			values.put("disable_on", "disabled");
		}

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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("POST " + request.getRequestURL().toString());

		// check if we are allowed to use cookies
		if (request.getParameter("ok_cookies") != null) {
			log.info("Saving cookie approval...");
			// save that we received permission, save for 1 hour
			Cookie cookie = new Cookie(COOKIES_OK, Boolean.TRUE.toString());
			cookie.setMaxAge(24 * 60 * 60);
			response.addCookie(cookie);
		}

		// check if we are NOT allowed to use cookies
		if (request.getParameter("no_cookies") != null) {
			log.info("Clearing cookies...");
			// also clears consent from before
			clearCookies(request, response);
		}

		response.sendRedirect(request.getRequestURI());
	}

	/**
	 * Gets the cookies from the HTTP request and maps the cookie name to the
	 * cookie object.
	 *
	 * @param request the HTTP request from web server
	 * @return map from cookie key to cookie value
	 */
	public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
		HashMap<String, Cookie> map = new HashMap<>();
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				map.put(cookie.getName(), cookie);
			}
		}

		return map;
	}

	/**
	 * Clears all of the cookies included in the HTTP request.
	 *
	 * @param request the HTTP request
	 * @param response the HTTP response
	 */
	public static void clearCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// update cookie values to trigger delete
				cookie.setValue(null);
				cookie.setMaxAge(0);

				// add new cookie to the response
				response.addCookie(cookie);
			}
		}
	}
}
