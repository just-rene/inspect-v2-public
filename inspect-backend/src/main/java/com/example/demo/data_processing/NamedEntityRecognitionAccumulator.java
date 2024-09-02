package com.example.demo.data_processing;


import com.example.demo.repos.JobRepository;
import com.example.demo.repos.NamedEntityRecognitionDayAccumulatedRepository;
import com.example.demo.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * desc algo
 * find occurrence of loc,per,misc,org in a article (once counted  < no longer find solution)
 * accumulate all occurrences over all article
 * save to db
 */

@Component
class NamedEntityRecognitionAccumulator implements IAccumulator {

    @Autowired
    private JobService jobService;


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private NamedEntityRecognitionDayAccumulatedRepository NamedEntityRecognitionDayAccumulatorRepository;

    @Autowired
    private MongoTemplate mt;

    //fixed date
    @Scheduled(fixedRate = 24 * 3_600_000)
    public void execute() {

        //this is GMT+2 Timezone, Mongo will -2 on this Datetime
        LocalDateTime start = LocalDateTime.now().withHour(2).withMinute(0).withSecond(0).withNano(0).minusDays(0);//change back to 1
        LocalDateTime end = start.plusDays(1);

        var x  = jobRepository.triggerNamedEntityRecognitionDayAccumulator(start, end);
        System.err.println("NamedEntityRecognitionDayAccumulator: " + x.size());
    }
}
