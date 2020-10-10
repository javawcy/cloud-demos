package dev.lowdad.cloud.serverdemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients(basePackages = "dev.lowdad.cloud")
public class ServerDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(ServerDemo1Application.class, args);
    }

}
