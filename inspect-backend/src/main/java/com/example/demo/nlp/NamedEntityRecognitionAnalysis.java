package com.example.demo.nlp;

import com.example.demo.huggingface.HuggingFaceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class NamedEntityRecognitionAnalysis {

    @Autowired
    private HuggingFaceRequest huggingFaceRequest;

    public String getAnalysis(String input){
        ResponseEntity<String> response;
        try {
            //throws 503, means model computes -> needs 2. request
            //doc: If the requested model is not loaded in memory, the Serverless Inference API will start by loading the model into memory and returning a 503 response, before it can respond with the prediction.
            response = huggingFaceRequest.makeRequest("{" + input +"}", HuggingFaceRequest.HuggingFaceModelUrl.NAMED_ENTITY_RECOGNITION);

        } catch (Exception e) {

            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                //throw new RuntimeException(ex);
            }
            response = huggingFaceRequest.makeRequest("{" + input + "}",HuggingFaceRequest.HuggingFaceModelUrl.NAMED_ENTITY_RECOGNITION);
            System.err.println(response.getStatusCode());
            System.err.println(response.getBody());
            return response.getBody();
        }

        System.err.println(response.getStatusCode());
        System.err.println(response.getBody());

        return response.getBody();

    }

}
