package com.lagou.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LagouCodeApplication8081 {

    public static void main(String[] args) {
        SpringApplication.run(LagouCodeApplication8081.class, args);
    }

}
