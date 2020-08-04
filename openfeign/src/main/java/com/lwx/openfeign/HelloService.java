package com.lwx.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author: drew
 * date:2020/8/4
 * time:15:31
 */
@FeignClient("provider")
public interface HelloService {
    @GetMapping("/hello")
    String hello();

    @GetMapping("hello2")
    String hello2(@RequestParam("name") String name);
    
}
