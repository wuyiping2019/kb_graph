package com.hk.kb_graph.security;


import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.domain.RespDo;
import com.hk.kb_graph.security.filter.UsernamePasswordAuthenticationExtendFilter;
import com.hk.kb_graph.security.handler.DefineUsernamePasswordAuthenticationFailureHandler;
import com.hk.kb_graph.security.handler.DefineUsernamePasswordAuthenticationSuccessHandler;
import com.hk.kb_graph.security.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

import java.io.PrintWriter;


@Configuration
@EnableWebSecurity(debug = true)
public class UsernamePasswordLoginSecurityConfig {
    @Value("${spring.security.login.username.parameter}")
    private String usernameParameter;
    @Value("${spring.security.login.password.parameter}")
    private String passwordParameter;
    @Value("${spring.security.login.validation.parameter}")
    private String validationParameter;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private UserDetailsPasswordService userDetailsPasswordService;

    @Autowired
    private AuthenticationEventPublisher authenticationEventPublisher;

    @Autowired
    private ObjectPostProcessor<Object> objectPostProcessor;

    /**
     * UsernamePasswordAuthentication的AuthenticationSuccessHandler
     */
    @Autowired
    private DefineUsernamePasswordAuthenticationSuccessHandler successHandler;
    /**
     * UsernamePasswordAuthentication的AuthenticationFailureHandler
     */
    @Autowired
    private DefineUsernamePasswordAuthenticationFailureHandler failureHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 创建一个DaoAuthenticationProvider对象，负责用户认证的具体工作
     */
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //设置密码编码器
        provider.setPasswordEncoder(passwordEncoder);
        //设置获取UserDetails的对象
        provider.setUserDetailsService(loginUserService);
        //设置更新用户密码的对象
        provider.setUserDetailsPasswordService(userDetailsPasswordService);
        return provider;
    }

    /**
     * 创建一个AuthenticationManager对象，管理DaoAuthenticationProvider
     **/
    public AuthenticationManager authenticationManager() throws Exception {
        return new AuthenticationManagerBuilder(objectPostProcessor)
                .authenticationEventPublisher(authenticationEventPublisher)
                .authenticationProvider(daoAuthenticationProvider()).build();
    }

    /**
     * 创建一个filter对象，用于登录认证
     * 注入authenticationManager方法创建的AuthenticationManager对象
     */
    public UsernamePasswordAuthenticationExtendFilter usernamePasswordAuthenticationFilterExtend() throws Exception {
        UsernamePasswordAuthenticationExtendFilter filter = new UsernamePasswordAuthenticationExtendFilter(usernameParameter,
                passwordParameter,
                validationParameter,
                successHandler,
                failureHandler,
                authenticationManager()
        );
        //设置拦截的url
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and()
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf()
                .disable();

        http.headers().frameOptions().sameOrigin();
        http.addFilterAt(usernamePasswordAuthenticationFilterExtend(), UsernamePasswordAuthenticationFilter.class);
        DefaultSecurityFilterChain build = http.build();
        return build;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }


}
