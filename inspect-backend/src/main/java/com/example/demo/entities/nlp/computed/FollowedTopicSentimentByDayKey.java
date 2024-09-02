package com.example.demo.entities.nlp.computed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class FollowedTopicSentimentByDayKey {
    public String name;
    public String entityGroup;
    public String date;
    public String label;
}
