package com.example.demo.repos;


import com.example.demo.entities.general.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {

    @Query("{'name': {$regex : ?0, $options: 'i'}})")
    List<Topic> findLike(String name);
}
