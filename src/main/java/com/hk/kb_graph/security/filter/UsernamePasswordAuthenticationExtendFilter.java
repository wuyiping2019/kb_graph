package com.hk.kb_graph.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.security.handler.DefineUsernamePasswordAuthenticationFailureHandler;
import com.hk.kb_graph.security.handler.DefineUsernamePasswordAuthenticationSuccessHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;



public class UsernamePasswordAuthenticationExtendFilter extends UsernamePasswordAuthenticationFilter {
    private String usernameParameter;
    private String passwordParameter;
    private String kaptchaParameter;


    public UsernamePasswordAuthenticationExtendFilter(String usernameParameter,
                                                      String passwordParameter,
                                                      String kaptchaParameter,
                                                      AuthenticationSuccessHandler successHandler,
                                                      AuthenticationFailureHandler failureHandler,
                                                      AuthenticationManager manager) {
        this.usernameParameter = usernameParameter;
        this.passwordParameter = passwordParameter;
        this.kaptchaParameter = kaptchaParameter;
        super.setAuthenticationSuccessHandler(successHandler);
        super.setAuthenticationFailureHandler(failureHandler);
        super.setAuthenticationManager(manager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //不是POST请求直接验证失败
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //查看request的content-type 如果是application/json 则自定义获取username和password的方式
        String contentType = request.getContentType();
        if (contentType == null) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        switch (contentType) {
            case "application/json":
                //json提交验证
                BufferedReader reader;
                try {
                    reader = request.getReader();
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                    JSONObject jsonObject = JSONObject.parseObject(sb.toString());
                    String username = (String) jsonObject.get(usernameParameter);
                    String password = (String) jsonObject.get(passwordParameter);
                    //使用相同的方式返回
                    UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
                    super.setDetails(request, authRequest);
                    return super.getAuthenticationManager().authenticate(authRequest);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case "application/x-www-form-urlencoded":
                String kaptcha = request.getParameter(kaptchaParameter);
                HttpSession session = request.getSession();
                String sessionKaptcha = (String) session.getAttribute(kaptchaParameter);
                if (kaptcha != null && kaptcha.equalsIgnoreCase(sessionKaptcha)) {
                    super.attemptAuthentication(request, response);
                }

        }
        return null;
    }
}
