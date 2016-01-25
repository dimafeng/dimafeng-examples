package com.dimafeng.examples.scala_spring

import com.dimafeng.examples.scala_spring.dao.BlogPostRepository
import com.dimafeng.examples.scala_spring.model.BlogPost
import com.dimafeng.examples.scala_spring.service.{BlogPostService, Link}
import org.mockito.Mock
import org.mockito.Mockito._


class BlogPostServiceSpec extends ServiceSpec(classOf[BlogPostService]) {

  @Mock var blogPostRepository: BlogPostRepository = _

  it should "generate proper collection of links for given categoryId" in {
    val blogPostService = new BlogPostService(blogPostRepository)

    when(blogPostRepository.findByCategoryId("123"))
      .thenReturn(Array(new BlogPost().copy(id = "321",name = "test blog post")))

    val result = blogPostService.blogPostsByCategoryId("123")

    assert(result == Seq(Link("test blog post", "/blog/321")))
  }

  it should "generate proper collection of links for given categoryId (sugar)" in {
    val blogPostRepository = mock[BlogPostRepository]
    val blogPostService = new BlogPostService(blogPostRepository)

    when(blogPostRepository.findByCategoryId("123"))
      .thenReturn(Array(new BlogPost().copy(id = "321",name = "test blog post")))

    val result = blogPostService.blogPostsByCategoryId("123")

    assert(result == Seq(Link("test blog post", "/blog/321")))
  }
}
