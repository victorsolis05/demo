package org.fasora.examples.uploadbucket.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/api/bucket")
public class BucketController {

    @GetMapping("/")
    public String test() throws IOException {
        return "{ \"name\" : \"gcp\" }";
    }

    @Value("classpath:test.json")
    Resource resourceFile;

    @GetMapping("/upload")
    public String upload() throws IOException {
        // The ID of your GCP project
        String projectId = "playground-s-11-bf51c029";

        // The ID of your GCS bucket
        String bucketName = "gs://test-bucket-v2/";

        // The ID of your GCS object
        String objectName = "test.json";

        // The path to your file to upload
        String filePath = "test.json";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        storage.create(blobInfo,  resourceFile.getInputStream().readAllBytes() /*Files.readAllBytes(Paths.get(filePath))*/);

        System.out.println(
                "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);

        return "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName;
    }
}
