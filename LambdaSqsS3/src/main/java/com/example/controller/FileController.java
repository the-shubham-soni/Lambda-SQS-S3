package com.example.controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.service.S3Service;

import com.example.service.SQSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/files")
public class FileController {
	private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final S3Service s3Service;
    private final SQSService sqsService;

    public FileController(S3Service s3Service, SQSService sqsService) {
        this.s3Service = s3Service;
        this.sqsService = sqsService;
    }

    @GetMapping
    public ResponseEntity<String> defaultEndpoint() {
        return ResponseEntity.ok("Welcome to the File API! Use /upload to upload a file.");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String key = file.getOriginalFilename();
            PutObjectResult po = s3Service.uploadFile(key, file);
            sqsService.sendMessage("File uploaded: " + key);
            return ResponseEntity.ok("File uploaded and message sent to SQS!");
        } catch (Exception e) {
            log.error("Failed to upload file or send SQS message", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the file.");
        }
    }
}
