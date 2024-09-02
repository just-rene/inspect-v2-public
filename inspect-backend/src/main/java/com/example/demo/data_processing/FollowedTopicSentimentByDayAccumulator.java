package com.example.demo.data_processing;


import com.example.demo.entities.general.FollowedTopic;
import com.example.demo.repos.FollowedTopicRepository;
import com.example.demo.repos.FollowedTopicSentimentByDayRepository;
import com.example.demo.repos.JobRepository;
import com.example.demo.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FollowedTopicSentimentByDayAccumulator implements IAccumulator {


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private FollowedTopicRepository followedTopicRepository;

    @Autowired
    private FollowedTopicSentimentByDayRepository followedTopicSentimentByDayRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Scheduled(fixedRate = 24 * 3_600_000)
    @Override
    public void execute() {

        //this is GMT+2 Timezone, Mongo will -2 on this Datetime
        LocalDateTime start = LocalDateTime.now().withHour(2).withMinute(0).withSecond(0).withNano(0).minusDays(0);//change back to 1
        LocalDateTime end = start.plusDays(1);

        for(FollowedTopic followedTopic : followedTopicRepository.findAll()){
            for (var topic : followedTopic.getTopics()){
                var result = jobRepository.triggerFollowedTopicSentimentByDay(start, end, topic.getName(), topic.getEntityGroup());
            }
        }
    }
}
