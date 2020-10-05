
public class RegexQuantifiers {
	public static void main(String[] args) {
		String text = "aardvark";
		String regex;

		regex = "a";
		System.out.println("\nRegex: " + regex);
		RegexHelper.showMatches(text, regex);

		System.out.println();
	}
}
