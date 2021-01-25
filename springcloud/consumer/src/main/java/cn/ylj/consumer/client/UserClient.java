package cn.ylj.consumer.client;

import cn.ylj.consumer.client.fallback.UserClientFallback;
import cn.ylj.consumer.config.FeignConfig;
import cn.ylj.consumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service", fallback = UserClientFallback.class,
        configuration = FeignConfig.class)
public interface UserClient {

    //动态代理自动拼接http://user-service/user/123
    @GetMapping("/user/{id}")
    User qureyById(@PathVariable Integer id);
}
