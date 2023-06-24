package com.A2P.dev.encryption;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Encrypt encrypt() {
        return new Encrypt();
    }
}
