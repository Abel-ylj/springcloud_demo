package cn.ylj.consumer.controller;

import cn.ylj.consumer.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author : yanglujian
 * create at:  2021/1/24  3:22 下午
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/{uId}")
    public User findById(@PathVariable("uId") Integer uId){
        User user = restTemplate.getForObject("http://localhost:9091/user/" + uId, User.class);
        System.out.println(user);
        return user;
    }
}