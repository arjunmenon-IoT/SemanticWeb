package com.emse.semweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SemwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemwebApplication.class, args);
    }

}
