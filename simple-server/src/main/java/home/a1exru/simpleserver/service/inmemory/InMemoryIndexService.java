package home.a1exru.simpleserver.service.inmemory;

import home.a1exru.simpleserver.dto.Document;
import home.a1exru.simpleserver.service.IndexService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InMemoryIndexService implements IndexService {

    private final Map<String, String> documents = new HashMap<>();
    private final Map<String, Set<String>> index = new HashMap<>();

    @Override
    public List<Document> search(String query) {
        String[] tokens = query.split("\\s");
        Map<String, Long> rank = Stream.of(tokens).filter(t -> t.length() > 0)
                .map(t -> t.replaceAll("\\W", ""))
                .map(String::toLowerCase)
                .map(t -> index.getOrDefault(t, new HashSet<>()))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return rank.entrySet().stream()
                .map(e -> new Document(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(Document::getRank, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public void put(String key, String document) {
        documents.put(key, document);

        String[] tokens = document.split("\\s");
        Stream.of(tokens).filter(t -> t.length() > 0)
                .map(t -> t.replaceAll("\\W", ""))
                .map(String::toLowerCase).forEach(t -> {
            Set<String> list = index.getOrDefault(t, new HashSet<>());
            index.put(t, list);
            list.add(document);
        });
    }

    @Override
    public Document get(String key) {
        String document = documents.get(key);
        return new Document(document, 0L);
    }
}