package home;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTests {
  private static final ObjectMapper OBJECT_MAPPER_1 = new ObjectMapper();
  private static final ObjectMapper OBJECT_MAPPER_2 =
      new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

  @Autowired KafkaConfiguration trc;
  @LocalServerPort int port;

  @Test
  void contextLoads() {
    System.out.println("@ConfigurationPropertiesScan works");
  }

  /*
    @Test
    void testRequestBodyRecord() throws Throwable {
      var uri = URI.create("http://localhost:" + port + Application.REQUEST_BODY_RECORD_ENDPOINT);
      TechnicalMetadataModel requestBody =
          new TechnicalMetadataModel();
      }
    }
  */
}
