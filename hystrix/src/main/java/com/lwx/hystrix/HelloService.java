package com.lwx.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * author: drew
 * date:2020/8/3
 * time:15:54
 */
@Service
public class HelloService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 再这个方法中，我们将发起一个远程调用。调用provider中的hello接口
     *
     * 但是，这个调用可能失败
     *
     * 我们再这个方法添加@HystrixCommand
     * @return
     */
    @HystrixCommand(fallbackMethod = "error")
    public String hello() {
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    /**
     * 注意这个方法名称要和fallbackMethod一致
     * @return
     */
    public String error() {
        return "error";
    }

}
