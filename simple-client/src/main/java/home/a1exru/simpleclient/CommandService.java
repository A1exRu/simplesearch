package home.a1exru.simpleclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandService implements CommandLineRunner {

    @Value("${api.search}")
    private String searchUrl;

    private Pattern pattern = Pattern.compile("^(\\w*)(?:\\s(.*))?$");

    private Map<String, Consumer<String>> handlers = new HashMap<>();

    {
        handlers.put("help", this::help);
        handlers.put("put", this::put);
        handlers.put("search", this::search);
        handlers.put("get", this::get);
        handlers.put("exit", this::exit);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Welcome to Search Client ===");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String next = scanner.nextLine();
            Matcher matcher = pattern.matcher(next);

            if (matcher.matches()) {
                String arg = matcher.groupCount() == 2 ? matcher.group(2) : "";
                Consumer<String> handler = handlers.getOrDefault(matcher.group(1), this::error);
                handler.accept(arg);
                System.out.println("--- Ok ---");
            }
        }
    }

    private void help(String arg) {
        System.out.println("Available commands: help, put <key> <text>, search <query>, exit");
    }

    private void error(String arg) {
        System.out.println("Unknown command");
    }

    private void put(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches() && matcher.group(2) != null) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation(searchUrl, new PutCommand(matcher.group(1), matcher.group(2)));
        } else {
            System.out.println("Required param missing: put <key> <text>");
        }
    }

    private void search(String query) {
        RestTemplate restTemplate = new RestTemplate();
        Response response = restTemplate.getForObject(searchUrl + "?query=" + query, Response.class);
        if (response.getDocuments() != null) {
            response.documents.stream().map(Document::getBody).forEach(System.out::println);
        }
    }

    private void get(String key) {
        RestTemplate restTemplate = new RestTemplate();
        Document response = restTemplate.getForObject(searchUrl + "/" + key, Document.class);
        System.out.println(response.getBody());

    }

    private void exit(String arg) {
        System.exit(0);
    }

    @Data
    @AllArgsConstructor
    public static class PutCommand {
        private String key;
        private String text;
    }

    @Data
    public static class Document {
        private String body;
        private Long rank;
    }

    @Data
    public static class Response {
        private String query;
        private List<Document> documents;
    }

}
