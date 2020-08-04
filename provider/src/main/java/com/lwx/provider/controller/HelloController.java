package com.lwx.provider.controller;

import com.lwx.commons.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
        public String hello() {
                return "hello" + port;
        }

        @GetMapping("/hello2")
        public String hello2(String name) {
                return "hello"+name;
        }

    /**
     * 这种事key/value
     * @param user
     * @return
     */
    @PostMapping("/user1")
    public User addUser(User user) {
            return user;
    }

    /**
     * post的传递方式有两种一种是json的
     * @param user
     * @return
     */
    @PostMapping("/user2")
    public User addUser2(@RequestBody User user) {
        return user;
    }
}
