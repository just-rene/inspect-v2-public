package com.example.demo.nlp;


import com.example.demo.entities.general.Job;
import com.example.demo.entities.nlp.*;
import com.example.demo.services.JobService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NlpScheduler {

        //todo: app conf
        private final boolean emoOn = true;

        @Autowired
        private EmotionMultilabelAnalysis emotionMultilabelAnalysis;

        @Autowired
        private SentimentAnalysis sentimentAnalysis;

        @Autowired
        private NamedEntityRecognitionAnalysis namedEntityRecognitionAnalysis;

        @Autowired
        private JobService jobService;


        @Scheduled(fixedRate = 60_000)
        private void execute() throws JsonProcessingException {
                ObjectMapper objectMapper = new ObjectMapper();
                //EMOTION MULTILABEL
                List<Job> notAnalysedJobsEmotionMultilabel =  jobService.getJobsWithoutNlpAnalysis(NlpTask.EMOTION_MULTILABEL);

                for(Job job : notAnalysedJobsEmotionMultilabel){
                        String jsonResult = emotionMultilabelAnalysis.getEmotionAnalysis(job.getScrap().getHeadline());
                        EmotionMultilabel emotionMultilabel = new EmotionMultilabel();

                        List<EmotionMultilabelPair> listOfResults = objectMapper.readValue("[" + jsonResult.substring(2, jsonResult.length()-2) +"]", new TypeReference<List<EmotionMultilabelPair>>(){});

                        emotionMultilabel.setResult(listOfResults);
                        job.setEmotionMultilabel(emotionMultilabel);
                        jobService.save(job);
                }

               //SENTIMENT
                var notAnalysedJobsSentiment =  jobService.getJobsWithoutNlpAnalysis(NlpTask.SENTIMENT);

                for(Job job : notAnalysedJobsSentiment){
                        String jsonResult = sentimentAnalysis.getAnalysis(job.getScrap().getHeadline());
                        Sentiment sentiment = new Sentiment();
                        List<SentimentPair> listOfResults =
                                objectMapper.readValue("[" + jsonResult.substring(2, jsonResult.length()-2) +"]", new TypeReference<List<SentimentPair>>(){});
                        sentiment.setResult(listOfResults);
                        job.setSentiment(sentiment);
                        jobService.save(job);
                }

                List<Job> notAnalysedJobsNer =  jobService.getJobsWithoutNlpAnalysis(NlpTask.NAMED_ENTITY_RECOGNITION);

                for(Job job : notAnalysedJobsNer){
                        String jsonResult = namedEntityRecognitionAnalysis.getAnalysis(job.getScrap().getContent());
                        NamedEntityRecognition ner = new NamedEntityRecognition();
                        List<NamedEntityRecognitionPair> listOfResults =
                                objectMapper.readValue(jsonResult, new TypeReference<List<NamedEntityRecognitionPair>>(){});
                        ner.setResult( listOfResults);
                        job.setNamedEntityRecognition(ner);
                        jobService.save(job);
                }
        }
}
