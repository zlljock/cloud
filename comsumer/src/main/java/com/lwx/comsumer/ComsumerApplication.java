package com.lwx.comsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ComsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComsumerApplication.class, args);
    }

    /**
     *使用Spring自制的Http传输
     * @return
     */
    @Bean
    RestTemplate  restTemplate(){
        return  new RestTemplate();
    }
}
