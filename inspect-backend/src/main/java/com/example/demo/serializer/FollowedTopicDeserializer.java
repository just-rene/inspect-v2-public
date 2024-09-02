package com.example.demo.serializer;


import com.example.demo.entities.general.FollowedTopic;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class FollowedTopicDeserializer extends JsonDeserializer {


    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public FollowedTopic deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException, JacksonException {

        FollowedTopic followedTopic = new FollowedTopic();

        var result  = objectMapper.readTree(jsonParser);
        followedTopic.setName(result.get("name").toString().substring(1,result.get("name").toString().length()-1));

        var topics = result.get("topics");
        if (topics.isArray()){
           for (int i = 0; i < topics.size(); i++){
               String name = topics.get(i).get("name").toString().substring(1,topics.get(i).get("name").toString().length()-1);
               String entityGroup = topics.get(i).get("entityGroup").toString().substring(1,topics.get(i).get("entityGroup").toString().length()-1);
               followedTopic.addTopic(name, entityGroup);
           }
        }
        return followedTopic;

    }

}