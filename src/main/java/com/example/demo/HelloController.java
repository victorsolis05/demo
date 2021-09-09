package com.example.demo;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
@Path("/hello")
@Singleton
public class HelloController {

    @GET
    public String sayHello() {
        return "Hello World";
    }

    @GET
    @Path("/upload")
    public String upload() throws IOException{
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
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
    
        System.out.println(
            "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
        
        return "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName;
    }

}
