package com.erinic.ssm.controller;

import com.erinic.ssm.domain.T_user;
import com.erinic.ssm.service.T_userService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by asus on 2017/4/4.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Resource
    private T_userService t_userService;


    @RequestMapping("/loginAdmin")
    public String login(T_user user,Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        try{
            subject.login(token);
            return "admin";
        }catch (Exception e){
            model.addAttribute("error","用户名或密码错误");
            return "../../login" ;
        }
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/student")
    public String student(){
        return "admin";
    }

    @RequestMapping("/teacher")
    public String teacher(){
        return "admin";
    }
}
