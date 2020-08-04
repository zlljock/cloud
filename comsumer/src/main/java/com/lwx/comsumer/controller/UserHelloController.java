package com.lwx.comsumer.controller;

import com.lwx.commons.User;
import org.apache.http.HttpConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


    @GetMapping("/hello4")
    public void hello4() {
        String s1 = restTemplate2.getForObject("http://provider/hello2?name={1}", String.class,"lwx");
        System.out.println(s1);
        ResponseEntity<String> responseEntity = restTemplate2.getForEntity("http://provider/hello2?name={1}", String.class, "lwx");
        String body = responseEntity.getBody();
        System.out.println(body);
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("HttpStatus"+statusCode);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println(statusCodeValue);
        HttpHeaders headers = responseEntity.getHeaders();
        Set<String> keySet = headers.keySet();
        System.out.println("==============header===========");
        for (String s : keySet) {
            System.out.println(s+":"+headers.get(s));
        }
    }


    /**
     *三种重载方法的传递方式
     */
    @GetMapping("/hello5")
    public void hello5() {
        String s1 = restTemplate2.getForObject("http://provider/hello2?name={1}", String.class,"lwx");
        System.out.println(s1);
        Map<String,Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        String s2 = restTemplate2.getForObject("http://provider/hello2?name={name}", String.class,map);
        System.out.println(s2);

    }

    @GetMapping("/hello6")
    public void hello6() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username", "lwx");
        map.add("password","123");
        map.add("id","99");
        User user = restTemplate2.postForObject("http://provider/user1", map, User.class);
        System.out.println(user);
        user.setId(98);
        User user2 = restTemplate2.postForObject("http://provider/user2", user, User.class);
        System.out.println(user2);
    }

    @GetMapping("/hello8")
    public void hello8() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username", "lwx");
        map.add("password","123");
        map.add("id","99");
        restTemplate2.put("http://provider/user1", map);


        User user = new User();
        user.setId(68);
        user.setUsername("we");
        user.setPassword("123");
        restTemplate2.put("http://provider/user2", user);

    }

    @GetMapping("/hello9")
    public void hello9(){
        restTemplate2.delete("http://provider/user1?id{1}",99);
        restTemplate2.delete("http://provider/user2/{1}",99);
    }
}
