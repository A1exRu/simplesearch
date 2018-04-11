package home.a1exru.simpleserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import home.a1exru.simpleserver.dto.Document;
import home.a1exru.simpleserver.service.IndexService;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @MockBean
    private IndexService indexService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Setter
    private JacksonTester<Map> jsonTester;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void search() throws Exception {
        List<Document> documents = Arrays.asList(new Document("A", 2L), new Document("B", 1L));
        given(indexService.search("abc")).willReturn(documents);

        Map result = this.restTemplate.getForObject("/search?query=abc", Map.class);
        Assertions.assertThat(jsonTester.write(result)).isEqualToJson("/search/search_response.json");

        verify(indexService).search("abc");
    }

    @Test
    public void get() throws Exception {
        given(indexService.get("test_key")).willReturn(new Document("Test_body", 0L));

        Map result = this.restTemplate.getForObject("/search/test_key", Map.class);
        Assertions.assertThat(jsonTester.write(result)).isEqualToJson("/search/get_response.json");

        verify(indexService).get("test_key");
    }

    @Test
    public void put() throws Exception {
        String jsonPath = "/search/put_request.json";
        HttpEntity<String> entity = getEntityFromJson(jsonPath);

        this.restTemplate.postForObject("/search", entity, Object.class);
        verify(indexService).put("test_key", "Abc");
    }

    private HttpEntity<String> getEntityFromJson(String jsonPath) throws URISyntaxException, IOException {
        URL url = getClass().getResource(jsonPath);
        Path resPath = Paths.get(url.toURI());
        String requestJson = new String(Files.readAllBytes(resPath), "UTF8");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(requestJson, headers);
    }

}