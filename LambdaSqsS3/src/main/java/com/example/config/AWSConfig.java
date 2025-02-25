package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Configuration
public class AWSConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.access-key}")
    private String accessKeyId;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withRegion(region);

        if (!accessKeyId.isEmpty() && !secretKey.isEmpty()) {
            builder.withCredentials(new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(accessKeyId, secretKey)));
        } else {
            // Use default credentials provider chain
            builder.withCredentials(new DefaultAWSCredentialsProviderChain());
        }

        return builder.build();
    }

    @Bean
    public AmazonSQS amazonSQS() {
        AmazonSQSClientBuilder builder = AmazonSQSClientBuilder.standard()
                .withRegion(region);

        if (!accessKeyId.isEmpty() && !secretKey.isEmpty()) {
            builder.withCredentials(new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(accessKeyId, secretKey)));
        } else {
            // Use default credentials provider chain
            builder.withCredentials(new DefaultAWSCredentialsProviderChain());
        }

        return builder.build();
    }
}
