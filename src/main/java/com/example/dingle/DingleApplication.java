package com.example.dingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingleApplication.class, args);
    }

}
