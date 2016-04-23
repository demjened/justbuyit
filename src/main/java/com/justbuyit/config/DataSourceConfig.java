package com.justbuyit.config;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.dao.hibernate.SimpleCompanyDAO;
import com.justbuyit.dao.hibernate.SimpleUserDAO;

/**
 * Hibernate powered data access configuration. 
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:config/env/env.${APP_CONFIG_MODE:dev}.properties" })
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    @Autowired
    SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        // create database schema
        new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).setName(environment.getRequiredProperty("db.name"))
                .addScript("classpath:db/create_schema.sql").build();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.justbuyit.entity" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("db.driver_class_name"));
        dataSource.setUrl(environment.getRequiredProperty("db.url"));
        dataSource.setUsername(environment.getRequiredProperty("db.username"));
        dataSource.setPassword(environment.getRequiredProperty("db.password"));
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("db.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("db.hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("db.hibernate.format_sql"));
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public CompanyDAO companyDAO() {
        SimpleCompanyDAO dao = new SimpleCompanyDAO();
        dao.setSessionFactory(sessionFactory);
        return dao;
    }

    @Bean
    public UserDAO userDAO() {
        SimpleUserDAO dao = new SimpleUserDAO();
        dao.setSessionFactory(sessionFactory);
        return dao;
    }

}
