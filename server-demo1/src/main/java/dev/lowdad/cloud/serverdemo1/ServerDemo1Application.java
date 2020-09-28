package dev.lowdad.cloud.serverdemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "dev.lowdad.cloud")
public class ServerDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(ServerDemo1Application.class, args);
    }

}
