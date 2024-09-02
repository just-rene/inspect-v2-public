package com.example.demo.huggingface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HuggingFaceRequest {

    public final static class HuggingFaceModelUrl {
        public static final String EMOTION_MULTILABEL = "https://api-inference.huggingface.co/models/cardiffnlp/twitter-roberta-base-emotion-multilabel-latest";
        public static final String SENTIMENT = "https://api-inference.huggingface.co/models/cardiffnlp/twitter-roberta-base-sentiment-latest";
        public static final String NAMED_ENTITY_RECOGNITION = "https://api-inference.huggingface.co/models/FacebookAI/xlm-roberta-large-finetuned-conll03-english";
    }

    private  RestTemplate restTemplate;

    @Value("${huggingface.bearer_token}")
    public String bearerToken;

    @Autowired
    public HuggingFaceRequest(RestTemplateBuilder builder) {
        this.restTemplate = builder.additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + bearerToken);
            return execution.execute(request, body);
        }).build();
    }


    public ResponseEntity<String> makeRequest(String input, String huggingFaceModelUrl){

        HttpEntity<String> request = new HttpEntity<>("{ inputs: "+ input +" }");

        return restTemplate.
                exchange(huggingFaceModelUrl,
                        HttpMethod.POST,
                        request,
                        String.class);
    }
}
