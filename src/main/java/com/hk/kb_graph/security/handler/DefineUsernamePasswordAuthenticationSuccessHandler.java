package com.hk.kb_graph.security.handler;

import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.domain.RespDo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 定义登录成功的处理逻辑
 * */
@Component
public class DefineUsernamePasswordAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Value("${spring.security.login.handler.success.redirect}")
    private boolean redirectFlag;
    @Value("${spring.security.login.handler.success.url}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        clearAuthenticationAttributes(request);
        if(redirectFlag){
            //配置登录成功执行跳转的话 跳转到指定的url
            request.getRequestDispatcher(redirectUrl).forward(request,response);
        }else {
            //不进行跳转 回写authentication
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            RespDo respDo = RespDo.newInstance().success("200", "登录成功", authentication);
            writer.write(JSONObject.toJSONString(respDo));
            writer.flush();
            writer.close();
        }
    }
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

}

