package home;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

class LearningRustInTest {
  private static final String FILENAME = "learning-rust-in-a-month-of-lunches.txt";
  private static final Path PATH_TXT = Path.of("src/test/resources", FILENAME);
  private static final Path PATH_HTML =
      Path.of("src/test/resources", FILENAME.replace(".txt", ".html"));
  private static final String CSS = """
      """;

  @Test
  void test() throws Throwable {
    Tuple2<String, Map<Integer, String>> tuple =
        Utils.extractPres(Files.readString(PATH_TXT, UTF_8));
    var document = Jsoup.parse(tuple._1());

    Utils.addStuff(document, "Learning Rust in a Month of Lunches", CSS);

    // insert the pre back and write to file
    Utils.insertPreBackThenWriteToFile(document, tuple._2(), PATH_HTML);
  }
}
