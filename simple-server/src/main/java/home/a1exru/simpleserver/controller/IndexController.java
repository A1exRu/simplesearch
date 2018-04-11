package home.a1exru.simpleserver.controller;

import home.a1exru.simpleserver.dto.Document;
import home.a1exru.simpleserver.service.IndexService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping
    public ResponseEntity<?> search(@RequestParam String query) {
        List<Document> documents = indexService.search(query);

        Map<String, Object> result = new HashMap<>();
        result.put("query", query);
        result.put("documents", documents);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> get(@PathVariable String key) {
        Document document = indexService.get(key);
        return ResponseEntity.ok(document);
    }

    @PostMapping
    public ResponseEntity<?> put(@RequestBody Command command) {
        indexService.put(command.getKey(), command.getText());
        return ResponseEntity.ok().build();
    }

    @Data
    public static class Command {
        String key;
        String text;
    }

}
