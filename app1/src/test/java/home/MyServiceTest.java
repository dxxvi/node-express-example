package home;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyServiceTest {
  @Autowired private MyService service;

  @Test
  void testOnly1canAccess() throws Exception {
    service.only1canAccess();
  }

  @Test
  void testOnly1canAccessNonOracle() throws Exception {
    service.only1canAccessNonOracle();
  }
}
