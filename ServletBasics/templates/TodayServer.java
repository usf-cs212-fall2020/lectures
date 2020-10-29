import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TodayServer {
	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		// TODO Fill in TodayServer.main(...) method
	}

	public static class TodayServlet extends HttpServlet {
		private static final long serialVersionUID = 202040;
		public static final String TITLE = "Today";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// System.out.println(Thread.currentThread().getName() + ": " + request.getRequestURI());
			// TODO Fill in TodayServlet.doGet(...) method
		}
	}

	public static String getDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}
}
