import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A quick demo of how to use groups in regular expressions.
 */
public class RegexGroups {
	/**
	 * Demonstrates how to extract different groups from a regular expression. Not
	 * meant to be a good regular expression for emails though! It breaks for some
	 * special cases.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// A simple test email. Keeping it simple to focus on groups.
		String email = "username@dons.usfca.edu";
		String html = String.format("<a href=\"mailto:%s\">%s</a>", email, "username@usfca.edu");

		// A regular expression to demonstrate how to extract different groups.
		String regex = "(?i)(\\w+)@(\\w+\\.([.\\w]+))";

		/*
		 * Notes: Group 0 is the entire match.
		 *
		 * The (?i) at the start does not contribute to group count. Any group that
		 * starts with a ? question mark is a non-capturing group.
		 *
		 * The first group, i.e. group 1, is the match for the first (.+).
		 *
		 * The @ at sign will not appear in any group (except group 0, which is the
		 * entire match).
		 *
		 * The second group is the match for (.+\\.(.+)) after the @ at sign.
		 *
		 * The third group is the nested group (.+) inside the second group. It will
		 * NOT include the last period (i.e. \\.).
		 */

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		System.out.println(email);

		if (matcher.matches()) {
			for (int i = 0; i <= matcher.groupCount(); i++) {
				System.out.printf("Group #%d: %s%n", i, matcher.group(i));
			}
		}

		System.out.println();
		System.out.println(html);

		matcher = pattern.matcher(html);

		while (matcher.find()) {
			for (int i = 0; i <= matcher.groupCount(); i++) {
				System.out.printf("Group #%d: %s%n", i, matcher.group(i));
			}
			System.out.println();
		}
	}
}
