package com.example.demo.entities.general;

import com.example.demo.entities.nlp.EmotionMultilabel;
import com.example.demo.entities.nlp.NamedEntityRecognition;
import com.example.demo.entities.nlp.Sentiment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document

@NoArgsConstructor
public class Job {

    @Getter
    @MongoId
    public ObjectId id;

    @Getter
    @Setter
    @Indexed(unique = true)
    public String url;

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public LocalDateTime localDateTime;

    @Getter
    @Setter
    public List<Report> reports = new ArrayList<>();

    @Getter
    @Setter
    public Scrap scrap;

    @Getter
    @Setter
    public EmotionMultilabel emotionMultilabel;

    @Getter
    @Setter
    public Sentiment sentiment;

    @Getter
    @Setter
    public NamedEntityRecognition namedEntityRecognition;


    public void addReport(Report report){
        this.reports.add(report);
    }



}
