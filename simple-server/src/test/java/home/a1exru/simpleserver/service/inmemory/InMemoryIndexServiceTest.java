package home.a1exru.simpleserver.service.inmemory;

import home.a1exru.simpleserver.dto.Document;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InMemoryIndexServiceTest {

    private InMemoryIndexService indexService = new InMemoryIndexService();

    @Test
    public void search() {
        String phrase = "You can do anything, but not everything";
        indexService.put("phrase", phrase);

        List<Document> result = indexService.search("Everything");
        assertNotNull(result);
        assertEquals(1, result.size());
        Document document = result.get(0);
        assertNotNull(document);
        assertEquals(phrase, document.getBody());
        assertEquals(Long.valueOf(1), document.getRank());
    }

    @Test
    public void search_multiDocuments() {
        String phrase1 = "You can do anything, but not everything";
        String phrase2 = "A customer can have a car painted any color he wants as long as it’s black";
        indexService.put("phrase1", phrase1);
        indexService.put("phrase2", phrase2);

        List<Document> result = indexService.search("Can I have a car?");
        assertNotNull(result);
        assertEquals(2, result.size());

        Document document1 = result.get(0);
        assertNotNull(document1);
        assertEquals(phrase2, document1.getBody());
        assertEquals(Long.valueOf(4), document1.getRank());

        Document document2 = result.get(1);
        assertNotNull(document2);
        assertEquals(phrase1, document2.getBody());
        assertEquals(Long.valueOf(1), document2.getRank());
    }

    @Test
    public void get() {
        String phrase1 = "You can do anything, but not everything";
        String phrase2 = "A customer can have a car painted any color he wants as long as it’s black";
        indexService.put("phrase1", phrase1);
        indexService.put("phrase2", phrase2);

        Document document1 = indexService.get("phrase1");
        Document document2 = indexService.get("phrase2");

        assertNotNull(document1);
        assertEquals(phrase1, document1.getBody());
        assertEquals(Long.valueOf(0), document1.getRank());

        assertNotNull(document2);
        assertEquals(phrase2, document2.getBody());
        assertEquals(Long.valueOf(0), document2.getRank());
    }

}