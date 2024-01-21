package com.logan.ndp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.io.PrintStream;

@EnableEurekaServer
@SpringBootApplication
@ConfigurationProperties(prefix = "server")
public class EurekaStarter {
    @Value("${server.port}")
    private static String port;
    public static void main(String[] args) {
        System.out.println(port);
        SpringApplication.run(EurekaStarter.class, args);

    }
}
