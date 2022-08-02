package com.hk.kb_graph.controller.security;

import com.hk.kb_graph.domain.RespDo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @PostMapping("/login")
    public RespDo login(Authentication authentication){
        //如果没有认证的话，通过post请求/login网址会被Sping Security的拦截器拦截
        //请求不会到此处
        //如果已经认证过了的话，通过post请求/login网址会被Spring Security放行
        return RespDo.newInstance().success("200", authentication.getName() + "已经登录", authentication);
    }
}
