package com.example.demo.rest;


import com.example.demo.entities.general.FollowedTopic;
import com.example.demo.entities.general.Topic;
import com.example.demo.entities.nlp.computed.FollowedTopicSentimentByDay;
import com.example.demo.services.FollowedTopicsService;
import com.example.demo.services.TopicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
public class TopicRest {

    @Autowired
    private FollowedTopicsService followedTopicsService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/api/topic/like/{name}")
    public List<Topic> findByName(@PathVariable @NotNull String name) {
        if (!name.isBlank()) {
            return topicService.findLike(name);
        }
        return Collections.emptyList();

    }

    @GetMapping("/api/followedTopic/addTopic/{followedTopicId}/{topicId}")
    public ResponseEntity<String> addTopicToFollowedTopic(@PathVariable @NotNull long followedTopicId, @PathVariable @NotNull long topicId) {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/api/followedTopic/sentimentDayAcc/{followedTopicName}")
    public ResponseEntity<List<FollowedTopicSentimentByDay>> sentimentDayAcc(@PathVariable @NotNull String followedTopicName) {
        FollowedTopic followedTopic = followedTopicsService.findByName(followedTopicName);
        List<FollowedTopicSentimentByDay> followedTopicSentimentByDayList = followedTopicsService.getSentimentDayAccumulatedBy(followedTopic);
        return new ResponseEntity<>(followedTopicSentimentByDayList, HttpStatus.OK);
    }

    @GetMapping("/api/followedTopic/getAll")
    public ResponseEntity<List<FollowedTopic>> getAllAllFollowedTopics() {
        return new ResponseEntity<>(followedTopicsService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/api/followedTopic/create")
    public ResponseEntity<FollowedTopic> create(@RequestBody FollowedTopic followedTopic) throws JsonProcessingException {
        FollowedTopic ft = followedTopicsService.create(followedTopic);
        return new ResponseEntity<>(ft, HttpStatus.OK);
    }

}
