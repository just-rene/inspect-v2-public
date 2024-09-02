package com.example.demo.entities.general;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Scrap {

    @MongoId
    public ObjectId id;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    public String headline;


    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    public String content;

}
