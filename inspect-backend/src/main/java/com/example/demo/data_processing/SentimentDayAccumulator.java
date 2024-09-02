package com.example.demo.data_processing;


import com.example.demo.repos.JobRepository;
import com.example.demo.repos.SentimentDayAccumulatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SentimentDayAccumulator implements IAccumulator {

    private final boolean recomputeDays = false;
    @Autowired
    private SentimentDayAccumulatorRepository sentimentDayAccumulatorRepository;

    @Autowired
    private JobRepository jobRepository;

    @Scheduled(fixedRate = 24 * 3_600_000)
    public void execute() {
        //this is GMT+2 Timezone, Mongo will -2 on this Datetime
        LocalDateTime start = LocalDateTime.now().withHour(2).withMinute(0).withSecond(0).withNano(0).minusDays(0);
        LocalDateTime end = start.plusDays(1);

        var x =  jobRepository.triggerSentimentDayAccumulator(start, end);
        System.err.println("sentimentDayAccumulatorRepository-size: " +  x.size());
        System.out.println("sentiment day saved");
    }
}
