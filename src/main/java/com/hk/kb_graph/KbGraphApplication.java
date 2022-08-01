package com.hk.kb_graph;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableAspectJAutoProxy(exposeProxy = true)
@EnableWebSecurity
public class KbGraphApplication {
    public static void main(String[] args) {
        SpringApplication.run(KbGraphApplication.class, args);
    }

}
