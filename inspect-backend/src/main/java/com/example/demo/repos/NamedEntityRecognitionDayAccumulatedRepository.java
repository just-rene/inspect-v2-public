package com.example.demo.repos;


import com.example.demo.entities.nlp.computed.NamedEntityRecognitionDayAccumulated;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NamedEntityRecognitionDayAccumulatedRepository extends MongoRepository<NamedEntityRecognitionDayAccumulated, String> {


    @Query("{ '_id.date' : ?0 , '_id.entityGroup' : ?1 }")
    List<NamedEntityRecognitionDayAccumulated> getByDate(String date, String entityGroup);

}
