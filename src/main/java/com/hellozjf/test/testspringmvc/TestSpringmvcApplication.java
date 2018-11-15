package com.hellozjf.test.testspringmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TestSpringmvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringmvcApplication.class, args);
    }
}
