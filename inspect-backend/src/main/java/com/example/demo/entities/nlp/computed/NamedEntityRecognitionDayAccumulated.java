package com.example.demo.entities.nlp.computed;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NamedEntityRecognitionDayAccumulated {
    private NamedEntityRecognitionDayAccumulatedResult _id;
    private int count;
}
