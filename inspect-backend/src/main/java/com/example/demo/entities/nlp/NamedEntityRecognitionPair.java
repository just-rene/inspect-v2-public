package com.example.demo.entities.nlp;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class NamedEntityRecognitionPair {

    @JsonProperty("entity_group")
    private String entityGroup;
    private float score;
    private String word;
    private int start;
    private int end;
}
