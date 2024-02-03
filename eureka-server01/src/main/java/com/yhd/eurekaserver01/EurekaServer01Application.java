package com.yhd.eurekaserver01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *张飞20240203
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer01Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer01Application.class, args);
    }

}
