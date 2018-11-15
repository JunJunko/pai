package com.jk.pai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jk.pai.controller")
public class SpringbootStartApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(SpringbootStartApplication.class, args);
    } 
}

