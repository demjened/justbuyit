package com.justbuyit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.InMemoryCompanyDAO;
import com.justbuyit.dao.InMemoryProfileDAO;
import com.justbuyit.dao.InMemorySubscriptionDAO;
import com.justbuyit.dao.InMemoryUserDAO;
import com.justbuyit.dao.ProfileDAO;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.dao.UserDAO;

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
    
    @Bean
    public CompanyDAO companyDAO() {
        return new InMemoryCompanyDAO();
    }
    
    @Bean
    public SubscriptionDAO subscriptionDAO() {
        return new InMemorySubscriptionDAO();
    }
    
    @Bean
    public UserDAO userDAO() {
        return new InMemoryUserDAO();
    }
    
    @Bean
    public ProfileDAO profileDAO() {
        return new InMemoryProfileDAO();
    }
    
}
