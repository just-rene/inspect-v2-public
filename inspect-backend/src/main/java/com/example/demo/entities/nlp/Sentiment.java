package com.example.demo.entities.nlp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
public class Sentiment {

    @Getter
    @Setter
    private List<SentimentPair> result;
}
