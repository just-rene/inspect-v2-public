package com.example.demo.scrapper;


import com.example.demo.services.JobService;
import org.apache.hc.core5.http.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component("mongo-scheduler")
public class ScrapperScheduler {



    @Autowired
    private IScrapper webscrapper1;


    @Autowired
    private JobService jobsService;




    //pro 1h
    @Scheduled(fixedRate = 3_600_000)
    private void execute() throws NotImplementedException {
        System.out.println("executing scrapper");
        webscrapper1.execute();
        throw new NotImplementedException("pls implement your webscrapper here!");

    }
}
