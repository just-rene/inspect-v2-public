package com.example.demo.services;


import com.example.demo.entities.general.Job;
import com.example.demo.nlp.NlpTask;
import com.example.demo.repos.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> list() {
        return jobRepository.findAll();
    }

    public Job getByUrl(String url){
        return jobRepository.findByUrl(url);
    }

    public Job doesSuccessfulJobExists(String url){
        return  jobRepository.doesSuccessfulJobExists(url);
    }

    public Job doesFailedJobExists(String url){
        return  jobRepository.doesFailedJobExists(url);
    }

    public void save(Job job){
        jobRepository.save(job);
    }

    public List<Job> getJobsForTimeBefore(LocalDateTime localDateTime){
        return jobRepository.getJobsForTimeBefore(localDateTime);
    }

    public List<Job> getJobsBetween(LocalDateTime timestampStart,LocalDateTime timestampEnd){
        return jobRepository.getJobsBetween(timestampStart,timestampEnd);
    }

    public List<Job> getJobsWithoutNlpAnalysis(String nlpTask){
        if(NlpTask.EMOTION_MULTILABEL.equals(nlpTask)){
            return jobRepository.getJobsWhereEmotionMultilabel(false);
        }
        if(NlpTask.SENTIMENT.equals(nlpTask)){
           return jobRepository.getJobsWhereSentiment(false);
        }
        if(NlpTask.NAMED_ENTITY_RECOGNITION.equals(nlpTask)){
            return jobRepository.getJobsWhereNamedEntityRecognition(false);
        }

        System.err.println("getJobsWithOrWithoutNlpAnalysis: unknown type!");
        return List.of(new Job());

    }

}
