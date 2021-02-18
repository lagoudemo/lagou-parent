package com.lagou.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LagouEmailApplication8082 {

    public static void main(String[] args) {
        SpringApplication.run(LagouEmailApplication8082.class, args);
    }

}
