package home.a1exru.simpleserver.service.inmemory;

import home.a1exru.simpleserver.dto.Document;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class InMemoryIndexServiceTest {

    private InMemoryIndexService indexService = new InMemoryIndexService();

    @Test
    public void get() {
        String phrase = "You can do anything, but not everything";
        indexService.put(phrase);

        List<Document> result = indexService.get("Everything");
        assertNotNull(result);
        assertEquals(1, result.size());
        Document document = result.get(0);
        assertNotNull(document);
        assertEquals(phrase, document.getBody());
        assertEquals(Long.valueOf(1), document.getRank());
    }

    @Test
    public void get_multiDocuments() {
        String phrase1 = "You can do anything, but not everything";
        String phrase2 = "A customer can have a car painted any color he wants as long as it’s black";
        indexService.put(phrase1);
        indexService.put(phrase2);

        List<Document> result = indexService.get("Can I have a car?");
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


}