package com.dimafeng.examples.scala_spring

import java.util

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.lyncode.jtwig.mvc.JtwigViewResolver
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Configuration, Bean}
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.{WebMvcConfigurerAdapter, EnableWebMvc}


object Application extends App with LazyLogging {
  logger.info("App is being started with {}", args)
  SpringApplication.run(classOf[Application], args: _*)
}

@SpringBootApplication
class Application

@Configuration
@EnableWebMvc
class WebConfig extends WebMvcConfigurerAdapter {

  @Bean
  def jackson2HttpMessageConverter(): MappingJackson2HttpMessageConverter =
    new MappingJackson2HttpMessageConverter(objectMapper())

  override def configureMessageConverters(converters: util.List[HttpMessageConverter[_]]): Unit = {
    converters.add(jackson2HttpMessageConverter())
  }

  @Bean
  def objectMapper(): ObjectMapper = {
    new ObjectMapper() {
      setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
      registerModule(DefaultScalaModule)
    }
  }

  @Bean
  def viewResolver(): ViewResolver = {
    new JtwigViewResolver() {
      setPrefix("classpath:/views/")
      setSuffix(".html")
    }
  }
}