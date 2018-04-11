package home.a1exru.simpleserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "body")
public class Document {

    private String body;
    private Long rank;

}
