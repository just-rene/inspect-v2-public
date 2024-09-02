package com.example.demo.repos;


import com.example.demo.entities.general.FollowedTopic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowedTopicRepository extends MongoRepository<FollowedTopic, Long> {

    @Query("{ name : ?0 }")
    FollowedTopic findByName(String name);
}

