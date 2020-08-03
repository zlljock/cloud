package com.lwx.comsumer.controller;

import org.apache.http.HttpConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * author: drew
 * date:2020/8/1
 * time:16:12
 */
@RestController
public class UserHelloController {
    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;
    @Autowired
    DiscoveryClient discoveryClient;
    @GetMapping("/hello1")
    public String hello(){
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:1113/hello");
            connection  = (HttpURLConnection)url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s = br.readLine();
                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 没有负载均衡的一定要加详细地址
     * @return
     */
    @GetMapping("/hello2")
    public String hello2(){
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = list.get(0);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        StringBuffer sb =new StringBuffer();
        sb.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello");
        String s = restTemplate.getForObject(sb.toString(), String.class);
        return s;
    }


     @Autowired
     @Qualifier("restTemplate2")
     private RestTemplate restTemplate2;
    @GetMapping("/hello3")
    public String hello3(){
       return restTemplate2.getForObject("http://provider/hello",String.class);
    }
}
