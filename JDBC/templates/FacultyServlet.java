import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class FacultyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Log.getLog();

	private final DatabaseConnector connector;

	private final String sqlSelect;
	private final String htmlHeader;
	private final String htmlRow;
	private final String htmlFooter;

	private static final Set<String> COLUMNS = Set.of("last", "first", "email", "twitter", "courses");
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	public FacultyServlet(DatabaseConnector connector) throws IOException {
		this.connector = connector;

		sqlSelect  = Files.readString(Path.of("sql",  "SELECT.sql"),  UTF_8);
		htmlHeader = Files.readString(Path.of("html", "header.html"), UTF_8);
		htmlRow    = Files.readString(Path.of("html", "row.html"),    UTF_8);
		htmlFooter = Files.readString(Path.of("html", "footer.html"), UTF_8);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info(request.getQueryString());

		String sort = null; // TODO
		boolean asc = false; // TODO

		String query = null; // TODO
		String field = null; // TODO

		boolean onTwitter = false; // TODO
		boolean hasFilter = false; // TODO

		StringBuilder sql = new StringBuilder(sqlSelect);

		if (onTwitter) {
			sql.append(System.lineSeparator());
			// TODO
		}

		if (hasFilter && !field.equals("courses")) {
			sql.append(System.lineSeparator());
			// TODO
		}

		sql.append(System.lineSeparator());
		// TODO

		if (hasFilter && field.equals("courses")) {
			sql.append(System.lineSeparator());
			// TODO
		}

		sql.append(System.lineSeparator());
		// TODO

		// end of sql query
		sql.append(";");
		log.info("SQL: {}", sql);

		// prepare action to use for links (keeps form settings)
		StringBuffer action = new StringBuffer();

		if (onTwitter) {
			action.append("&twitter=on");
		}

		if (hasFilter) {
			// TODO
		}

		// prepare html output
		PrintWriter out = response.getWriter();
		out.println(getHeader(action.toString(), sort, asc));

		try (
				Connection db = connector.getConnection();
				PreparedStatement statement = db.prepareStatement(sql.toString());
		) {
			// TODO

			try (ResultSet results = statement.executeQuery()) {
				while (results.next()) {
					Map<String, String> values = new HashMap<>();

					// TODO

					out.println(StringSubstitutor.replace(htmlRow, values));
					out.println();
				}
			}
		}
		catch (SQLException e) {
			log.warn(e);
		}

		out.println(getFooter(sort, asc));
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	private String getHeader(String filter, String sort, boolean asc) throws IOException {
		Map<String, String> values = new HashMap<>();
		values.put("last",    "true");
		values.put("email",   "true");
		values.put("twitter", "true");
		values.put("courses", "true");

		values.put("filter", filter);
		values.put(sort, Boolean.toString(!asc));

		return StringSubstitutor.replace(htmlHeader, values);
	}

	private String getFooter(String sort, boolean asc) throws IOException {
		Map<String, String> values = new HashMap<>();
		values.put("sort", sort);
		values.put("asc", Boolean.toString(asc));
		values.put("thread", Thread.currentThread().getName());
		values.put("date", getLongDate());
		return StringSubstitutor.replace(htmlFooter, values);
	}

	public static String getColumn(HttpServletRequest request, String key, String value) {
		String found = request.getParameter(key);
		return found != null && COLUMNS.contains(found) ? found : value;
	}

	public static boolean isEqual(HttpServletRequest request, String key, String value) {
		String found = request.getParameter(key);
		return found != null && found.equalsIgnoreCase(value);
	}

	public static String escape(ResultSet results, String column) throws SQLException {
		return StringEscapeUtils.escapeHtml4(results.getString(column));
	}

	public static String getLongDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}
}
