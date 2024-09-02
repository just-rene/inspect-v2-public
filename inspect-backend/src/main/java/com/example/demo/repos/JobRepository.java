package com.example.demo.repos;



import com.example.demo.entities.general.Job;
import com.example.demo.entities.nlp.computed.FollowedTopicSentimentByDay;
import com.example.demo.entities.nlp.computed.NamedEntityRecognitionDayAccumulated;
import com.example.demo.entities.nlp.computed.SentimentDayAccumulated;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//MONGO
@Repository
public interface JobRepository extends MongoRepository<Job, String> {

    @Query(value = "{url:?0}")
    Job findByUrl(String url);

    @Query(value = "{url: ?0, 'reports.status': true}")
    Job doesSuccessfulJobExists(String url);

    @Query(value = "{url: ?0, 'reports.status': false}")
    Job doesFailedJobExists(String url);

    @Query(value = "{'localDateTime': {'$lt':  ?0   } }")
    List<Job> getJobsForTimeBefore(LocalDateTime localDateTime);
    //todo: deactivation of fields
    @Query(value = "{'localDateTime': {'$gte' : ?0 , '$lt':  ?1   } }")
    List<Job> getJobsBetween(LocalDateTime timestampStart,LocalDateTime timestampEnd);

    @Query(value = "{ emotionMultilabel: { $exists: ?0 } }")
    List<Job> getJobsWhereEmotionMultilabel(boolean exists);

    @Query(value = "{ sentiment: { $exists: ?0 } }")
    List<Job> getJobsWhereSentiment(boolean exists);

    @Query(value = "{ namedEntityRecognition: { $exists: ?0 } }")
    List<Job> getJobsWhereNamedEntityRecognition(boolean exists);


    //this will trigger a save operation to the -namedEntityRecognitionDayAccumulated- collection
    @Aggregation( pipeline = {
            "{ $match: { localDateTime: { $gt: ?0, $lt: ?1  } } }",
            "{ $project: { 'namedEntityRecognition.result': 1 } }",
            "{ $unwind: '$namedEntityRecognition.result' }",
            "{ $group: { _id: {   date: {$dateToString: { format: '%Y-%m-%d', date: ?0 }}, 'entityGroup': '$namedEntityRecognition.result.entityGroup', 'word': '$namedEntityRecognition.result.word' }, count: { $sum: 1 } } }",
            "{ $sort: { 'count': -1 } }",
            "{ $merge: { into: 'namedEntityRecognitionDayAccumulated', on: '_id', whenMatched: 'replace', whenNotMatched: 'insert' } }"
    })
    List<NamedEntityRecognitionDayAccumulated> triggerNamedEntityRecognitionDayAccumulator(LocalDateTime start, LocalDateTime end);


    //this will trigger a save operation to the -sentimentDayAccumulated- collection
    @Aggregation(pipeline = {
            "{ $match: { localDateTime: { $gt: ?0, $lt: ?1 }}}",
            "{ $project: {  sentiment: 1     } }",
            "{ $unwind: '$sentiment.result'   }",
            "{ $group: { _id: { label: '$sentiment.result.label' , date: {$dateToString: { format: '%Y-%m-%d', date: ?0 }}  }, subtotal: { $sum: '$sentiment.result.score' }, count: { $sum: 1 }}}",
            "{ $project: { result: { $divide: ['$subtotal', '$count'] } }}",
            "{ $merge: { into: 'sentimentDayAccumulated', on: '_id', whenMatched: 'replace', whenNotMatched: 'insert' } }"

    })
    List<SentimentDayAccumulated> triggerSentimentDayAccumulator(LocalDateTime start, LocalDateTime end);

    //this will trigger a save operation to the -followedTopicSentimentByDay- collection
    //notice: this pipeline is actually meant for an input array of multiple topics
    @Aggregation(pipeline = {
            "{ $match: { localDateTime: { $gt: ?0 , $lt: ?1 } } }",
            "{ $match: { $or: [{'namedEntityRecognition.result.word' : ?2 ,'namedEntityRecognition.result.entityGroup' : ?3}] }}",
            "{ $project: { sentiment: 1 } }",
            "{ $unwind: '$sentiment.result' }",
            "{ $group: { _id: { label: '$sentiment.result.label', date: { $dateToString: { format: '%Y-%m-%d', date: ?0 } },  'name': ?2 , 'entityGroup' : ?3    }, subtotal: { $sum: '$sentiment.result.score' }, count: { $sum: 1 } } }",
            "{ $project: { result: { $divide: ['$subtotal', '$count'] } } }",
            "{ $merge: { into: 'followedTopicSentimentByDay', on: '_id', whenMatched: 'replace', whenNotMatched: 'insert' } }"
    })
    List<FollowedTopicSentimentByDay> triggerFollowedTopicSentimentByDay(LocalDateTime start, LocalDateTime end, String topicName, String topicEntityGroup);

}