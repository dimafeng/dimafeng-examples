package com.dimafeng.examples.dynamic_bean_def;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationExample3 {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationExample3.class, args);
    }

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new DefaultFiltersBeanFactoryPostProcessor();
    }
}
