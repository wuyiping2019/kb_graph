package com.hk.kb_graph;


import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

@SpringBootTest
public class SecurityConfigTest implements ApplicationContextAware {
    private ApplicationContext context;
    @Autowired
    private WebApplicationContext delegatingFilterProxy;
    @Test
    public void catFilterChainProxy(){
        FilterChainProxy proxy = context.getBean(FilterChainProxy.class);
        System.out.println();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
