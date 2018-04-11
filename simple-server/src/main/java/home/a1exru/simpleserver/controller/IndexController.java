package home.a1exru.simpleserver.controller;

import home.a1exru.simpleserver.dto.Document;
import home.a1exru.simpleserver.service.IndexService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/search")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping
    public ResponseEntity<?> get(@RequestParam String query) {
        List<Document> documents = indexService.get(query);

        Map<String, Object> result = new HashMap<>();
        result.put("query", query);
        result.put("documents", documents);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> put(@RequestBody Command command) {
        indexService.put(command.getText());
        return ResponseEntity.ok().build();
    }

    @Data
    public static class Command {
        String text;
    }

}
