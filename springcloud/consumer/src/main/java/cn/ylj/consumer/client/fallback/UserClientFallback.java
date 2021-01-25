package cn.ylj.consumer.client.fallback;

import cn.ylj.consumer.client.UserClient;
import cn.ylj.consumer.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public User qureyById(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("用户异常");
        return user;
    }

}
