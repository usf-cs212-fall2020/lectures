import java.io.IOException;
import java.nio.file.Path;

public class TextFileParser {

	public static String removePunctuation(String text) {
		return text.replaceAll("(?U)\\p{Punct}+", "");
	}

	public static void main(String[] args) throws IOException {
		Path path = Path.of("sally.txt");
		System.out.println(path);
	}
}
