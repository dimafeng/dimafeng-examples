package com.dimafeng.examples.dynamic_bean_def;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class Example2 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        System.out.println(applicationContext.getBean("testBean"));
    }

    @Configuration
    public static class Config {

        @Bean
        public String myTestStringBean() {
            return "My test String Bean";
        }

        @Bean
        public BeanFactoryPostProcessor beanFactoryPostProcessor() {
            return bf -> {
                BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) bf;

                beanFactory.registerBeanDefinition("testBean",
                        BeanDefinitionBuilder.genericBeanDefinition(TestBean.class)
                                .addConstructorArgReference("myTestStringBean")
                                .getBeanDefinition()
                );
            };
        }
    }

    public static class TestBean {
        private String value;

        public TestBean(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Test bean with value: " + value;
        }
    }
}
