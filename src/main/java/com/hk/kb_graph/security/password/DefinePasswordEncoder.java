package com.hk.kb_graph.security.password;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
public class DefinePasswordEncoder {
    @Value("${spring.security.login.password.encoder}")
    private String passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder(){
        switch (passwordEncoder){
            case "noop":
                return NoOpPasswordEncoder.getInstance();
        }
        return null;
    }
}
