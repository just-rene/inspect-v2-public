package com.example.demo.rest;


import com.example.demo.entities.nlp.computed.SentimentDayAccumulated;
import com.example.demo.services.SentimentDayAccumulatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SentimentRest {

    @Autowired
    private SentimentDayAccumulatedService sentimentDayAccumulatedService;

    @CrossOrigin
    @GetMapping("/api/sentiment/all/limit/{limit}")
    public List<SentimentDayAccumulated> getAll(@PathVariable int limit){
        return sentimentDayAccumulatedService.getAll(limit);
    }
}
