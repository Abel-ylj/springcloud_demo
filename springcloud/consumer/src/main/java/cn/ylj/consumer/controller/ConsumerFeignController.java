package cn.ylj.consumer.controller;

import cn.ylj.consumer.client.UserClient;
import cn.ylj.consumer.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 使用Feign
 * 动态代理技术，代理类完成rest请求，负载均衡，服务降级处理
 *
 * @author : yanglujian
 * create at:  2021/1/25  9:38 上午
 */
@RestController
@RequestMapping("/custom_feign")
public class ConsumerFeignController {

    @Resource
    private UserClient userClient;

    @GetMapping("/{id}")
    public User queryById(@PathVariable Integer id){
        return userClient.qureyById(id);
    }
}