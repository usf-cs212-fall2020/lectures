import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to help debug and understand regular expressions.
 */
public final class RegexHelper {
	/** Example string for testing regular expressions. **/
	public static final String sample = "Sally Sue sells 76 sea-shells, by   the sea_shore.";

	/**
	 * Prints all of the matches found in the provided text. Will escape the
	 * newline character if found.
	 *
	 * @param text text to search in
	 * @param regex regular expression to search for
	 */
	public static void printMatches(String text, String regex) {
		ArrayList<String> matches = getMatches(text, regex);

		// replace newline characters with \n symbol for better output
		for (int i = 0; i < matches.size(); i++) {
			matches.set(i, matches.get(i).replaceAll("\n", "\\\\n"));
		}

		System.out.printf("%s: %s%n", regex, matches);
	}

	/**
	 * Returns a list of all the matches found in the provided text.
	 *
	 * @param text text to search in
	 * @param regex regular expression to search for
	 * @return list of all matches found in text
	 */
	public static ArrayList<String> getMatches(String text, String regex) {
		ArrayList<String> matches = new ArrayList<String>();

		// create regex and matcher
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);

		int index = 0;

		// keep going while found a match in text
		while (index < text.length() && m.find(index)) {

			// store matching substring
			matches.add(text.substring(m.start(), m.end()));

			if (m.start() == m.end()) {
				// advance index if matched empty string
				index = m.end() + 1;
			}
			else {
				// otherwise start looking at end of last match
				index = m.end();
			}
		}

		return matches;
	}

	/**
	 * Prints out the matching groups underneath the original string.
	 *
	 * @param text text to search in
	 * @param regex regular expression to search for
	 */
	public static void showMatches(String text, String regex) {
		// create regex and matcher
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);

		// character array to show where matches are located
		char fill = '_';
		char[] region = new char[text.length()];
		Arrays.fill(region, fill);

		// used to track current match
		int index = 0;
		int count = 0;

		// keep going while found a match in text
		while (index < text.length() && m.find(index)) {
			// converts count to a character
			// will loop through 0-9 and then A-Z as necessary
			fill = Character.forDigit(count % 36, 36);
			fill = Character.toUpperCase(fill);

			// indicate region matched
			Arrays.fill(region, m.start(), m.end(), fill);

			// test if matched empty string
			if (m.start() == m.end()) {
				// indicate empty string at location was matched
				region[m.start()] = '*';

				// move to next position to avoid infinite loop
				index++;
			}
			else {
				// otherwise start looking at end of last match
				index = m.end();
			}

			// increment count
			count++;
		}

		// print original string and matching regions
		System.out.println(text);
		System.out.println(region);
	}
}
