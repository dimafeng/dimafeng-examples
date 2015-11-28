package com.dimafeng.examples.dynamic_bean_def;


import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

public class Example1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        try {
            System.out.println(applicationContext.getBean("testBean"));
        } catch (NoSuchBeanDefinitionException e) {
            System.out.println("Bean not found");
        }

        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();

        beanFactory.registerBeanDefinition("testBean",
                BeanDefinitionBuilder.genericBeanDefinition(String.class)
                        .addConstructorArgValue("test")
                        .getBeanDefinition()
        );

        System.out.println(applicationContext.getBean("testBean"));
    }

    @Configuration
    public static class Config {

    }
}
