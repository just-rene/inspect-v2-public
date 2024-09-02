package com.example.demo.entities.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@NoArgsConstructor
public class Report {

    @Getter
    @Setter
    public LocalDateTime timestamp;

    @Getter
    @Setter
    public boolean wasSuccessful;




}
