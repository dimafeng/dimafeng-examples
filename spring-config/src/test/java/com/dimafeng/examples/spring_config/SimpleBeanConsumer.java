package com.dimafeng.examples.spring_config;

public class SimpleBeanConsumer {
    SimpleBean simpleBean;

    public SimpleBeanConsumer(SimpleBean simpleBean) {
        this.simpleBean = simpleBean;
    }
}
