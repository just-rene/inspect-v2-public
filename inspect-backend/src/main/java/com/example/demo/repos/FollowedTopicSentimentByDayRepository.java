package com.example.demo.repos;


import com.example.demo.entities.nlp.computed.FollowedTopicSentimentByDay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowedTopicSentimentByDayRepository extends MongoRepository<FollowedTopicSentimentByDay, Long> {

    @Query("{'_id.name': ?0, '_id.entityGroup': ?1, '_id.date': ?2 }")
    List<FollowedTopicSentimentByDay> findByByNameAndEntityGroup(String name, String entityType, String date);

    @Query(value = "{'_id.name': ?0, '_id.entityGroup': ?1}",sort = "{ '_id.date' : 1 }")
    List<FollowedTopicSentimentByDay> findByByNameAndEntityGroup(String name, String entityType);

}
