package io.github.korzepadawid.hackernewsapi.filestorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Service
class AwsS3FileStorageService implements FileStorageService {

    private static final Logger log = LoggerFactory.getLogger(AwsS3FileStorageService.class);

    private final S3AsyncClient s3AsyncClient;
    private final AwsS3Config awsS3Config;

    AwsS3FileStorageService(final S3AsyncClient s3AsyncClient,
                            final AwsS3Config awsS3Config) {
        this.s3AsyncClient = s3AsyncClient;
        this.awsS3Config = awsS3Config;
    }


    @Override
    @Async
    public void putFile(final String storageKey, final File file) {
        final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .key(storageKey)
                .bucket(awsS3Config.getBucketName())
                .build();
        final AsyncRequestBody asyncRequestBody = AsyncRequestBody.fromFile(file);
        final CompletableFuture<PutObjectResponse> future = s3AsyncClient.putObject(putObjectRequest, asyncRequestBody);
        future.whenComplete((putObjectResponse, throwable) -> handleResponse(storageKey, putObjectResponse, throwable));
        future.join();
    }

    @Override
    @Async
    public void deleteFile(final String storageKey) {
        final DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .key(storageKey)
                .bucket(awsS3Config.getBucketName())
                .build();
        final CompletableFuture<DeleteObjectResponse> future = s3AsyncClient.deleteObject(deleteObjectRequest);
        future.whenComplete((deleteObjectResponse, throwable) -> handleResponse(storageKey, deleteObjectResponse, throwable));
        future.join();
    }

    private void handleResponse(final String storageKey, final S3Response s3Response, final Throwable throwable) {
        if (s3Response != null) {
            log.info("s3 {} success", storageKey);
        } else {
            log.error("s3: an error occurred : {}", throwable.getMessage());
        }
    }
}
