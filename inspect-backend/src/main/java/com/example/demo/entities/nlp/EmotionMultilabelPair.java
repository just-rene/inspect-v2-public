package com.example.demo.entities.nlp;

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
public class EmotionMultilabelPair {
    private String label;
    private float score;
}
