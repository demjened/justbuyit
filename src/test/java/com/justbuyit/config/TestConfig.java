package com.justbuyit.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.inject.internal.Lists;
import com.justbuyit.auth.OAuthService;
import com.justbuyit.auth.OpenIdAuthenticator;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.entity.User;

/**
 * Configuration for integration tests that overrides application integration beans (eg. DAO, security).
 */
@Configuration
@ComponentScan(basePackages = { "com.justbuyit.eventprocessor" })
public class TestConfig {

    private class TestCompanyDAO implements CompanyDAO {

        private final List<Company> companies = Lists.newArrayList();
        
        public TestCompanyDAO() {
            Company c1 = new Company();
            c1.setUuid("COMPANY1");
            c1.setName("C1");
            c1.setSubscriptionEditionCode("FREE");
            c1.setSubscriptionStatus("ACTIVE");
            
            User u1 = new User();
            u1.setUuid("USER1");
            u1.setOpenId("O1");
            u1.setFirstName("U1");
            
            User u2 = new User();
            u2.setUuid("USER2");
            u2.setOpenId("O2");
            u2.setFirstName("U2");

            c1.getUsers().add(u1);
            c1.getUsers().add(u2);
            
            companies.add(c1);
        }
        
        @Override
        public Company create(Company company) {
            return null;
        }

        @Override
        public Company update(Company company) {
            return null;
        }

        @Override
        public void delete(Company company) {
        }

        @Override
        public Company findById(String companyId) {
            return null;
        }

        @Override
        public List<Company> findAll() {
            return companies;
        }
        
    }
    
    @Bean
    public OAuthService oAuthService() {
        return null;
    }
    
    @Bean
    public OpenIdAuthenticator openIdAuthenticator() {
        return null;
    }
    
    @Bean
    public CompanyDAO companyDAO() {
        return new TestCompanyDAO();
    }
    
    @Bean
    public UserDAO userDAO() {
        return null;
    }
    
}
