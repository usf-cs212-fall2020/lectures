public class RegexQuiz {

	public static void main(String[] args) {
		String text = null;
		String regex = null;

		System.out.println(text);
		System.out.println(regex);

		text = "hubbub";
		// RegexHelper.printMatches(text, "h.*b");
		// RegexHelper.printMatches(text, "h.*?b");
		// RegexHelper.printMatches(text, "h.*bb*");
		// RegexHelper.printMatches(text, "h.*bb+");
		// RegexHelper.printMatches(text, "h.*bb?");
		// RegexHelper.printMatches(text, "h.*?bb?");

		System.out.println();

		text = "ant ape bat bee bug cat cow dog";
		// RegexHelper.printMatches(text, "\\w*a\\w*");
		// RegexHelper.printMatches(text, "\\w+a\\w+");
		// RegexHelper.printMatches(text, "\\w+t\\b");
		// RegexHelper.printMatches(text, "\\w*[^e]e\\b");

		System.out.println();

		text = "dragonfly";
		regex = "(drag(on))(fly)";

		// TODO
	}

}
