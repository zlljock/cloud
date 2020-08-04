package com.lwx.openfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: drew
 * date:2020/8/4
 * time:15:32
 */
@RestController
public class HellcController {
    @Autowired
    HelloService helloService;
    @GetMapping("hello")
    public String hello(){
        return helloService.hello();
    }
}
