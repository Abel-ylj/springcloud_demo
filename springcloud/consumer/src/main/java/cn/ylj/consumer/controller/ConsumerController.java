package cn.ylj.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
@Slf4j
@DefaultProperties(defaultFallback = "defaultFallBack")
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;


//    @HystrixCommand(fallbackMethod = "findByIdFallback")//返回值必须是String，被修饰的方法返回值也要改
    @RequestMapping("/{uId}")
    @HystrixCommand
    public String findById(@PathVariable("uId") Integer uId){

        //直接用服务名 ， server-name  ===》 ip 中间会加入负载均衡逻辑
        String url = "http://user-service/user/" + uId;

        //从注册中心获取服务，解耦，不直接依赖具体
        String user = restTemplate.getForObject(url, String.class);
        System.out.println(user);
        return user;
    }

    public String findByIdFallback(Integer uId){
        log.error("查询消息失败. id:{}", uId);
        return "对不起，网络太拥堵了";
    }

    public String defaultFallBack(){
        log.error("默认回退");
        return "默认回退：对不起，网络太拥堵了";
    }
}