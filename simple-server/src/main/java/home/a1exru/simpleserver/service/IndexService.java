package home.a1exru.simpleserver.service;

import home.a1exru.simpleserver.dto.Document;

import java.util.List;
import java.util.Set;

public interface IndexService {

    List<Document> get(String query);

    void put(String document);

}
