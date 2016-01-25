package com.dimafeng.examples.scala_spring

import com.dimafeng.examples.scala_spring.model.BlogPost
import com.dimafeng.examples.scala_spring.service.Link
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class LinkTest extends FlatSpec with Matchers {
  behavior of classOf[Link].getSimpleName

  it should "create a proper link for an instance of BlogPost" in {
    val link = Link.createLink(new BlogPost().copy(name = "test name", id = "42"))

    //assert(link.title == "name test")
    //assert(link.url == "/blog/42")
    link.title should be === "test name"
  }
}
