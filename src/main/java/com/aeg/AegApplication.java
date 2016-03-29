package com.aeg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@EnableAutoConfiguration
public class AegApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(AegApplication.class, args);
    }
}
