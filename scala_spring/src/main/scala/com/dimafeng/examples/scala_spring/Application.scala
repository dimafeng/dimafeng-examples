package com.dimafeng.examples.scala_spring

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.lyncode.jtwig.mvc.JtwigViewResolver
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.ViewResolver


object Application extends App with LazyLogging {
  logger.info("App is being started with {}", args)
  SpringApplication.run(classOf[Application], args: _*)
}

@SpringBootApplication
class Application {
  /**
    * Custom Jackson mapper
    */
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