package com.learn.cloud.hystrixserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
//@ServletComponentScan(basePackages = "com.learn.cloud.hystrixserver.**")
public class HystrixServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixServerApplication.class, args);
    }

}
