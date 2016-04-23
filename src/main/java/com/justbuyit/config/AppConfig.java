package com.justbuyit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.justbuyit.auth.OAuthService;

/**
 * Main application configuration that initializes
 * <ul>
 * <li>Authentication</li>
 * <li>Event processors</li>
 * <li>Data sources</li>
 * </ul>
 */
@Configuration
@ComponentScan(basePackages = { "com.justbuyit.auth", "com.justbuyit.eventprocessor" })
@Import(DataSourceConfig.class)
public class AppConfig {

    @Value("#{environment['OAUTH_CONSUMER_KEY']}")
    private String consumerKey;

    @Value("#{environment['OAUTH_CONSUMER_SECRET']}")
    private String consumerSecret;

    @Bean
    public OAuthService oAuthService() {
        return new OAuthService(consumerKey, consumerSecret);
    }

}
