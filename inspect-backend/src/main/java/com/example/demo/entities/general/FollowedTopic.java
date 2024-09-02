package com.example.demo.entities.general;


import com.example.demo.serializer.FollowedTopicDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.*;

@Document
@NoArgsConstructor
@JsonDeserialize(using = FollowedTopicDeserializer.class)
public class FollowedTopic {

    @Getter
    @MongoId
    private ObjectId _id;

    @Getter
    @Setter
    private String name;

    @Setter
    @Getter
    private List<Topic> topics = new ArrayList<>();

    public void addTopic(String topicName, String entityGroup){
        //set doesn't work here for some reason
        if (this.getTopics().stream().noneMatch( x -> Objects.equals(x.name, topicName) && Objects.equals(x.entityGroup, entityGroup))){
            this.topics.add(new Topic(topicName, entityGroup));
        }
    }

    @Document
    @AllArgsConstructor
    public class Topic  {

        @Setter
        @Getter
        private String name;

        @Setter
        @Getter
        private String entityGroup;

    }

}
