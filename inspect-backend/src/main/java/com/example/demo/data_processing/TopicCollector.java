package com.example.demo.data_processing;

import com.example.demo.entities.general.Topic;
import com.example.demo.entities.nlp.computed.NamedEntityRecognitionDayAccumulated;
import com.example.demo.repos.NamedEntityRecognitionDayAccumulatedRepository;
import com.example.demo.repos.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicCollector implements IAccumulator {


    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private NamedEntityRecognitionDayAccumulatedRepository namedEntityRecognitionDayAccumulatedRepository;

    @Autowired
    private ObjectMapper mapper;


    @Override
    @Scheduled(fixedRate = 24 * 3_600_000)
    public void execute() {

        List<NamedEntityRecognitionDayAccumulated> allNerDayAccList = namedEntityRecognitionDayAccumulatedRepository.findAll();

        for (NamedEntityRecognitionDayAccumulated ner : allNerDayAccList) {
            try {
                topicRepository.save(new Topic(ner.get_id().getWord(), ner.get_id().getEntityGroup()));
            } catch (Exception _) {}
        }
    }
}
