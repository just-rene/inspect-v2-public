package com.example.demo.services;

import com.example.demo.entities.general.Topic;
import com.example.demo.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> findLike(String name){
        return topicRepository.findLike(name);
    }

}
