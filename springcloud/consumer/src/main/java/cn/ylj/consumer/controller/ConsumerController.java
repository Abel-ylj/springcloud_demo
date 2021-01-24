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
        //直接用服务名 ， server-name  ===》 ip 中间会加入负载均衡逻辑
        String url = "http://user-service/user/" + uId;

        //从注册中心获取服务，解耦，不直接依赖具体
        User user = restTemplate.getForObject(url, User.class);
        System.out.println(user);
        return user;
    }
}