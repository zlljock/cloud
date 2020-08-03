package com.lwx.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: drew
 * date:2020/8/1
 * time:16:05
 */
@RestController
public class HelloController {
        @Value("${server.port}")
        Integer port;
        @GetMapping("/hello")
        public String hello(){
            return "hello"+port;
        }
}
