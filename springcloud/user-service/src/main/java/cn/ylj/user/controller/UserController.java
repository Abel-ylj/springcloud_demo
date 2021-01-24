package cn.ylj.user.controller;

import cn.ylj.user.entity.User;
import cn.ylj.user.service.UserService;
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

    @GetMapping("/{id}")
    public User queryById(@PathVariable Long id){
        return userService.queryById(id);
    }
}
