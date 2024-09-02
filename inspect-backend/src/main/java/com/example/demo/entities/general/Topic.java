package com.example.demo.entities.general;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
@CompoundIndexes(
        @CompoundIndex(name = "topicId", def = "{'name' : 1, 'namedEntityRecognitionType' : 1}", unique = true)
)
public class Topic  {
    private String name;
    private String entityGroup;
}

