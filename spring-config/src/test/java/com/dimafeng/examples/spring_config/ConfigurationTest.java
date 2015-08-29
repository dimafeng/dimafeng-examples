package com.dimafeng.examples.spring_config;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConfigurationTest.Config.class})
public class ConfigurationTest {

    @Autowired
    SimpleBeanConsumer consumer;

    @Test
    public void test()
    {
        assertNotNull(consumer);
    }

    @Configuration
    public static class Config {

        @Bean
        public SimpleBean simpleBean()
        {
            return new SimpleBean();
        }

        @Bean
        public SimpleBeanConsumer simpleBeanConsumer()
        {
            return new SimpleBeanConsumer(simpleBean());
        }
    }
}
