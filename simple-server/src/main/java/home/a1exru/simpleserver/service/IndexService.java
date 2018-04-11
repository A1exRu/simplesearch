package home.a1exru.simpleserver.service;

import home.a1exru.simpleserver.dto.Document;

import java.util.List;

public interface IndexService {

    List<Document> search(String query);

    void put(String key, String document);

    Document get(String key);

}
