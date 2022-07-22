package io.github.korzepadawid.hackernewsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class HackerNewsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackerNewsApiApplication.class, args);
    }

}
