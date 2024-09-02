package com.example.demo.rest;


import com.example.demo.entities.general.Job;
import com.example.demo.services.JobService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class JobsRest {

    @Autowired
    private JobService jobService;

    @GetMapping(value="/api/jobs/from/{timestampStart}/to/{timestampEnd}")
    public List<Job>  getJobsFromTo(@PathVariable @NotNull LocalDateTime timestampStart, @PathVariable @NotNull LocalDateTime timestampEnd){
        return jobService.getJobsBetween(timestampStart, timestampEnd);
    }
}
