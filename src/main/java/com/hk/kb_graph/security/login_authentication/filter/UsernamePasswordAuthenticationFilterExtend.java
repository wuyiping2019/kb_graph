package com.hk.kb_graph.security.login_authentication.filter;

import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.domain.RespDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class UsernamePasswordAuthenticationFilterExtend extends UsernamePasswordAuthenticationFilter {
    private final String usernameParameter;
    private final String kaptchaParameter;
    private final String passwordParameter;

    public UsernamePasswordAuthenticationFilterExtend(String usernameParameter, String passwordParameter, String kaptchaParameter) {
        this.usernameParameter = usernameParameter;
        this.kaptchaParameter = kaptchaParameter;
        this.passwordParameter = passwordParameter;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        RespDo success = RespDo.newInstance().success("200", "success", authResult);
        response.setHeader("Content-Type", "application/json;charsetEncoding=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(success));
        writer.flush();
        writer.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        RespDo success = RespDo.newInstance().fail("200", "failure");
        response.setHeader("Content-Type", "application/json");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(success));
        writer.flush();
        writer.close();
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
