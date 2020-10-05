public class RegexWordParsing {
	public static void main(String[] args) {
		String text = RegexHelper.sample;
		String regex = "";

		regex = "s";
		System.out.println("Regex: " + regex);
		RegexHelper.showMatches(text, regex);

		System.out.println();
	}
}
