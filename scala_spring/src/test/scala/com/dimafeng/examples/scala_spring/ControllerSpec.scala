package com.dimafeng.examples.scala_spring

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestContextManager
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(classOf[JUnitRunner])
abstract class ControllerSpec(controllerClass: Class[_]) extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {
  behavior of controllerClass.getSimpleName

  var mvc: MockMvc = _
  @Autowired
  val webApplicationContext: WebApplicationContext = null

  before {
    new TestContextManager(this.getClass).prepareTestInstance(this)
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
  }
}
