package com.lagou.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LagouUserApplication8080 {

    public static void main(String[] args) {
        SpringApplication.run(LagouUserApplication8080.class, args);
    }

}
