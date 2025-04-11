This is an example showing how to do distributed locking using PESSIMISTIC_WRITE in db.

This example requires a PostgreSQL database with a table named `MY_LOCK` which has only 1 column `ID`.

```
docker run -d --name postgres-container -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres:alpine
```

```java
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Main {
  public static void main(String[] args) throws Exception {
    long fileSize = -1;

    while (true) {
      var file = new File("/dev/shm/h1.png");
      if (!file.exists()) {
        Thread.sleep(1_500);
        continue;
      }

      if (file.length() == fileSize) {
        Thread.sleep(1_500);
        continue;
      }

      fileSize = file.length();
      var processBuilder = new ProcessBuilder("tesseract", "h1.png", "h1", "-l", "eng");
      processBuilder.directory(new File("/dev/shm"));

      file = new File("/dev/shm/h1.txt");
      if (file.exists()) {
        String everything = Files.readString(file.toPath(), StandardCharsets.UTF_8);
      }
      Thread.sleep(1000);
    }
  }
}
```
