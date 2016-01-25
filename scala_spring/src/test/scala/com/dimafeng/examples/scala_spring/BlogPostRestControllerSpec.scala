package com.dimafeng.examples.scala_spring

import com.dimafeng.examples.scala_spring.ReportApiControllerSpec._
import com.dimafeng.examples.scala_spring.controller.BlogPostRestController
import com.dimafeng.examples.scala_spring.service.BlogPostService
import org.mockito.Mockito
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders._
import org.springframework.test.web.servlet.result.MockMvcResultMatchers._


@ContextConfiguration(classes = Array(classOf[Config], classOf[WebConfig]))
@WebAppConfiguration
class BlogPostRestControllerSpec extends ControllerSpec(classOf[BlogPostRestController]) with BeforeAndAfterEach {

  it should "assign categories to a blog post on /blogPosts/1234/addCategory" in {
    mvc.perform(post("/blogPosts/1234/addCategory")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"categoryId\": [\"111\", \"222\"] }"))
      .andExpect(status().isOk())
    verify(blogPostService).addCategoryToBlog("1234", Array("111", "222"))
  }

  override def beforeEach() = {
    super.beforeEach()
    reset(blogPostService)
  }
}

object ReportApiControllerSpec {

  val blogPostService = Mockito.mock(classOf[BlogPostService])

  @Configuration
  class Config {

    @Bean def blogPostServiceBean = blogPostService

    @Bean def controller = new BlogPostRestController(blogPostService)
  }

}