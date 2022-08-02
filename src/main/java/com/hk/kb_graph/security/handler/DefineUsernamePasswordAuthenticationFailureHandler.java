package com.hk.kb_graph.security.handler;

import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.domain.RespDo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class DefineUsernamePasswordAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Value("${spring.security.login.handler.failure.redirect}")
    private boolean redirectFlag;
    @Value("${spring.security.login.handler.failure.url}")
    private String redirectUrl;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (redirectFlag) {
            request.getRequestDispatcher(redirectUrl).forward(request, response);
        } else {
            String uri = request.getRequestURI();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            if (uri.startsWith("/login")) {
                RespDo respDo = RespDo.newInstance().fail("400", "登录失败");
                writer.write(JSONObject.toJSONString(respDo));
                writer.flush();
                writer.close();
            } else {
                RespDo respDo = RespDo.newInstance().fail("400", "请登录");
                writer.write(JSONObject.toJSONString(respDo));
                writer.flush();
                writer.close();
            }
        }
    }
}
