package com.lwx.comsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.rtf.RTFEditorKit;

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

    /**
     * 此时的RestTemplate就有了负载负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate2(){
        return new RestTemplate();
    }
}
