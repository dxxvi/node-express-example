package home.ignore_me;

import static org.junit.jupiter.api.Assertions.*;

import home.Tuple2;
import home.Utils;
import java.util.Map;
import org.junit.jupiter.api.Test;

class UtilsTest {
  @Test
  void testExtractPres() {
    String html = """
        a<pre>b</pre>c
        <pre class="d">e</pre>""";
    Tuple2<String, Map<Integer, String>> tuple = Utils.extractPres(html);
    assertEquals("""
        a<i id="2"></i>c
        <i id="1"></i>""", tuple._1());
    assertTrue(tuple._2().containsKey(1), "no id of 1 in map");
    assertEquals("""
        <pre class="d">e</pre>""", tuple._2().get(1));
    assertTrue(tuple._2().containsKey(2), "no id of 2 in map");
    assertEquals("<pre>b</pre>", tuple._2().get(2));
  }
}
