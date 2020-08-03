package com.lwx.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * author: drew
 * date:2020/8/3
 * time:15:57
 */
@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

    @GetMapping("hello2")
    public void hello2() {
        HelloCommand helloCommand = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("lwx")), restTemplate);
        String execute = helloCommand.execute();//直接执行
        System.out.println(execute);
        /*try {
        Future<String> queue = helloCommand.queue();//先入队再 执行
            String s = queue.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }
}
