package com.example.demo.services;


import com.example.demo.entities.general.FollowedTopic;
import com.example.demo.entities.nlp.computed.FollowedTopicSentimentByDay;
import com.example.demo.repos.FollowedTopicRepository;
import com.example.demo.repos.FollowedTopicSentimentByDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowedTopicsService{

    @Autowired
    private FollowedTopicRepository followedTopicsRepository;

    @Autowired
    private FollowedTopicSentimentByDayRepository followedTopicSentimentByDayRepository;

    public FollowedTopic create(FollowedTopic followedTopic) {

        if (followedTopic.getTopics().isEmpty() || followedTopic.getName().isBlank()) {
            throw new IllegalArgumentException("At least 1 Topic is needed! Name must not be blank");
        }
        return followedTopicsRepository.save(followedTopic);
    }

    public List<FollowedTopic> findAll() {
        return followedTopicsRepository.findAll();
    }

    public List<FollowedTopicSentimentByDay> getSentimentDayAccumulatedBy(FollowedTopic followedTopic) {

        List<FollowedTopicSentimentByDay> followedTopicSentimentByDayList = new ArrayList<>();

        for (FollowedTopic.Topic topic : followedTopic.getTopics()) {
            followedTopicSentimentByDayList.addAll(followedTopicSentimentByDayRepository.findByByNameAndEntityGroup(topic.getName(), topic.getEntityGroup()));
        }

        return followedTopicSentimentByDayList;
    }

    public FollowedTopic findByName(String name) {
        return followedTopicsRepository.findByName(name);
    }
}
