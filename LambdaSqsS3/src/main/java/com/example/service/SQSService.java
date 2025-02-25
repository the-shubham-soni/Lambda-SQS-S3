package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@Service
public class SQSService {
    private final AmazonSQS amazonSQS;

//    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    @Autowired
    public SQSService(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    public void sendMessage(String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message);
        amazonSQS.sendMessage(sendMessageRequest);
    }
}
