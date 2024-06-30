package home.books;

import static java.nio.charset.StandardCharsets.UTF_8;

import home.Tuple2;
import home.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

class LearningRustInTest {
  private static final String FILENAME =
      Utils.decodeThenDecryptThenDecode(
          "iVAjZOE2mLRT5AxOqLzbKl4tbMH0PYEIXapClpWiPMLCCMvfdC33DeN68tloPe0G4_Iep4wyigwqc5MKFeFMNA");
  private static final Path PATH_TXT = Path.of("src/test/resources", FILENAME);
  private static final Path PATH_HTML =
      Path.of("src/test/resources", FILENAME.replace(".txt", ".html"));
  private static final String CSS =
      """
      :root {
        --pre-background: #eef6fa;
      }
      a { text-decoration: none }
      pre { background: linear-gradient(90deg, var(--pre-background), #fff, var(--pre-background));
        padding: .7rem 1rem; margin-left: 1rem
      }
      code.fm-code-in-text { background: var(--pre-background); padding: .1rem .2rem }
      pre span.fm-combinumeral { font-size: 1.4rem; line-height: 1 }
      p.fm-code-annotation { margin-top: .2rem; margin-bottom: .2rem }
      li > p { margin-top: .2rem; margin-bottom: .2rem }
      div.orm-ChapterReader-codeSnippetContainer { display: flex }
      div.orm-ChapterReader-snippetButtonContainer { margin-top: 1rem; margin-left: 1rem }
      p + div.orm-ChapterReader-codeSnippetContainer > pre { margin-top: 0 }
      .float-left { float: left }
      .clear-both { clear: both }
      h1, h2, h3 { font-family: 'Noto Sans' }
      h1 { font-size: 1.67rem; font-weight: 300; color: #666 }
      h2 { font-size: 1.32rem; font-weight: 400; color: #26f }
      h3 { font-size: 1.15rem; font-weight: 400; color: #f26 }
      div.flex { display: flex }
      div#toc { position: fixed; top: 0; right: 3rem; background-color: rgba(255, 255, 255, .9);
        max-height: 82vh; overflow: auto; z-index: 9; padding: 1rem; padding-top: .1rem;
        padding-bottom: .5rem; border: 1px solid #ccc; border-top: 0
      }
      #toc-chkbox + div { display: none } #toc-chkbox:checked + div { display: unset }
      div#toc a { display: block; line-height: 1.45; color: #333 }
      div#toc a:hover { color: #26f }
      div#toc a.toc-1 { padding-top: 1rem }
      div#toc a.toc-2 { padding-left: 1rem }
      div#toc a.toc-3 { padding-left: 2rem }
      div#toc a.toc-4 { padding-left: 3rem }""";

  @Test
  void test() throws Throwable {
    Tuple2<String, Map<Integer, String>> tuple =
        Utils.extractPres(Files.readString(PATH_TXT, UTF_8));
    var document = Jsoup.parse(tuple._1());

    Utils.addStuff(
        document,
        Utils.decodeThenDecryptThenDecode(
            "I-UppN2YhY3PJna2uTeDPKnBqOpm4Fe7QbH7dtQq25u4V9jRMRRbG03oA-OmlHXqHEvZb5E5cIsdTeP1lZpqJQ"),
        CSS);

    // specific for each book
    document.select(".calibre5").forEach(el -> el.removeClass("calibre5"));
    document.select("p.body").forEach(p -> p.removeClass("body"));
    document
        .select("div.orm-ChapterReader-codeSnippetContainer + p.fm-code-annotation")
        .forEach(
            p -> {
              Element div = p.previousElementSibling();
              Element innerDiv = div.select("div.orm-ChapterReader-snippetButtonContainer").get(0);
              while (p != null) {
                Element nextP = p.nextElementSibling(); // can be null
                innerDiv.appendChild(p); // no need to remove p as it'll be re-parented
                if (nextP == null || !nextP.hasClass("fm-code-annotation") || !nextP.nameIs("p")) {
                  p = null;
                } else {
                  p = nextP;
                }
              }
            });
    document
        .select("p + div.orm-ChapterReader-codeSnippetContainer")
        .forEach(
            div -> {
              Element p = div.previousElementSibling();
              if (p.text().endsWith(":")) {
                Element newDiv = document.createElement("div");
                newDiv.addClass("flex");
                p.before(newDiv);
                newDiv.appendChild(p);
                newDiv.appendChild(div);
              }
            });
    document
        .select("p.co-summary-head + ul + *")
        .forEach(
            el -> {
              el.addClass("clear-both");
              Element ul = el.previousElementSibling();
              ul.addClass("float-left");
              Element p = ul.previousElementSibling();
              p.addClass("float-left");
            });

    // build the TOC
    Element tocDiv = document.createElement("div").attr("id", "toc");
    tocDiv.append(
        """
        <label for="toc-chkbox" style="cursor: pointer">Table of Contents</label>
        <input type="checkbox" id="toc-chkbox" style="visibility: hidden">""");
    Element innerTocDiv = document.createElement("div");
    AtomicInteger ai = new AtomicInteger();
    document
        .select("h1, h2, h3")
        .forEach(
            h -> {
              String id = "_id" + ai.incrementAndGet();
              h.attr("id", id);
              String cssClass = "toc-3";
              if (h.nameIs("h1")) {
                cssClass = "toc-1";
              } else if (h.nameIs("h2")) {
                cssClass = "toc-2";
              }
              innerTocDiv.append(
                  """
                  <a href="#%s" class="%s">%s</a>"""
                      .formatted(id, cssClass, h.text()));
            });
    tocDiv.appendChild(innerTocDiv);
    document.body().appendChild(tocDiv);

    // insert the pre back and write to file
    Utils.insertPreBackThenWriteToFile(document, tuple._2(), PATH_HTML);
  }
}
