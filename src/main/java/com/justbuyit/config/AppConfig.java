package com.justbuyit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.justbuyit.auth.ConnectionSigner;

@Configuration
@ComponentScan(basePackages = { "com.justbuyit" })
public class AppConfig {
    
    @Value("#{environment['OAUTH_CONSUMER_KEY']}")
    private String consumerKey;

    @Value("#{environment['OAUTH_CONSUMER_SECRET']}")
    private String consumerSecret;
    
    @Bean
    public ConnectionSigner connectionSigner() {
        return new ConnectionSigner(consumerKey, consumerSecret);
    }

}
