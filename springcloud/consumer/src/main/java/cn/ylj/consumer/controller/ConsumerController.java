package cn.ylj.consumer.controller;

import cn.ylj.consumer.entity.User;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : yanglujian
 * create at:  2021/1/24  3:22 下午
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;


    @RequestMapping("/{uId}")
    public User findById(@PathVariable("uId") Integer uId){
        //获取叫user-serivce的服务(高可用的话有多个)
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        //选取第一个
        ServiceInstance serviceInstance = instances.get(0);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/user/" + uId;

//        User user = restTemplate.getForObject("http://localhost:9091/user/" + uId, User.class);
        //从注册中心获取服务，解耦，不直接依赖具体
        User user = restTemplate.getForObject(url, User.class);
        System.out.println(user);
        return user;
    }
}