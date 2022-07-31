package io.github.korzepadawid.hackernewsapi.filestorage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Configuration
@ConfigurationProperties("aws.config")
public class AwsS3Config {

    private String region;
    private String bucketName;

    @Bean
    public S3AsyncClient s3AsyncClient() {
        return S3AsyncClient.builder()
                .region(Region.of(region))
                .build();
    }

    String getRegion() {
        return region;
    }

    void setRegion(final String region) {
        this.region = region;
    }

    String getBucketName() {
        return bucketName;
    }

    void setBucketName(final String bucketName) {
        this.bucketName = bucketName;
    }
}
