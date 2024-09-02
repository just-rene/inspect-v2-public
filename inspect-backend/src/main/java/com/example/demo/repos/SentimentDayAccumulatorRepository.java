package com.example.demo.repos;


import com.example.demo.entities.nlp.computed.SentimentDayAccumulated;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//todo remove day
@Repository
public interface  SentimentDayAccumulatorRepository extends MongoRepository<SentimentDayAccumulated, Long> {

}
