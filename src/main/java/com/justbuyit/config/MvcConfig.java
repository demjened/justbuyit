package com.justbuyit.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Spring MVC and Freemarker template engine configuration. 
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.justbuyit.controller" })
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean(name = "viewResolver")
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setSuffix(".ftl");
        return resolver;
    }

    @Bean(name = "freemarkerConfig")
    public FreeMarkerConfig freeMarkerConfig() {
        Properties props = new Properties();
        props.setProperty("number_format", "0.##");
        props.setProperty("locale", "en-GB");

        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setFreemarkerSettings(props);
        configurer.setTemplateLoaderPath("/WEB-INF/ftl/");
        return configurer;
    }

}
