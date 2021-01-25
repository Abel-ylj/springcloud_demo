package cn.ylj.user.controller;

import cn.ylj.user.entity.User;
import cn.ylj.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //从配置文件中获取的hotString,配置从配置中心服务中获取，配置中心读取git仓库
    //演示 用户修改仓库中的yml后，发送触发请求给 配置中心，完成整个微服务的热更新。
    @Value("${test.hotString}")
    private String hotString;

    @GetMapping("/{id}")
    public User queryById(@PathVariable Long id){

        User user = userService.queryById(id);
        user.setName(hotString);//hotString 热更新
        return user;
    }
}
