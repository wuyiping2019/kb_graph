package com.hk.kb_graph.security;


import com.hk.kb_graph.security.login_authentication.filter.UsernamePasswordAuthenticationFilterExtend;
import com.hk.kb_graph.security.login_authentication.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${spring.security.login.username.parameter}")
    private String usernameParameter;
    @Value("${spring.security.login.password.parameter}")
    private String passwordParameter;
    @Value("${spring.security.login.validation.parameter}")
    private String validationParameter;
    @Autowired
    private LoginUserService loginUserService;
    @Bean
    public AuthenticationManager providerManager(){
        DaoAuthenticationConfigurer configurer = new DaoAuthenticationConfigurer(loginUserService);
        configurer.passwordEncoder(NoOpPasswordEncoder.getInstance());
        return new ProviderManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        UsernamePasswordAuthenticationFilterExtend filterExtend = new UsernamePasswordAuthenticationFilterExtend(usernameParameter, passwordParameter, validationParameter);
        filterExtend.setAuthenticationManager(providerManager());
        HttpSecurity http = httpSecurity
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .authenticationManager(providerManager())
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .csrf().disable();
        return http.build();
    }

}
