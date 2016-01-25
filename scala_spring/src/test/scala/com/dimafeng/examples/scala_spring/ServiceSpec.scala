package com.dimafeng.examples.scala_spring

import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
abstract class ServiceSpec(cls: Class[_]) extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfterEach {
  behavior of cls.getSimpleName

  override def beforeEach() = MockitoAnnotations.initMocks(this)
}
