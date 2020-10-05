public class RegexGroups {
	public static void main(String[] args) {
		String email = "username@subdomain.example.com";
		String html = String.format("<a href=\"mailto:%s\">%s</a>", email, "username@usfca.edu");

		// TODO
		String regex = "";

		// TODO

		System.out.println(html);
		System.out.println(email);
		System.out.println(regex);

		// System.out.printf("Group #%d: %s%n", i, matcher.group(i));
	}
}
