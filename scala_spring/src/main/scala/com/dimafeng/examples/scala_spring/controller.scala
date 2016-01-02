package com.dimafeng.examples.scala_spring.controller

import com.dimafeng.examples.scala_spring.controller.Controller._
import com.dimafeng.examples.scala_spring.model.{BlogPost, Category, User}
import com.dimafeng.examples.scala_spring.service.{BlogPostService, CategoryService, UserService}
import com.dimafeng.examples.scala_spring.utils.JavaUtils._
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.{Pointcut, Aspect, Around}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.{Component, Controller}
import org.springframework.web.bind.annotation._
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping(Array("/users"))
class UserRestController @Autowired()(userService: UserService) {

  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST))
  def add(@RequestBody user: User): User = userService.add(user)

  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET))
  def all(): Seq[User] = userService.findAll()
}

@RestController
@RequestMapping(Array("/blogPosts"))
class BlogPostRestController @Autowired()(blogPostService: BlogPostService) {

  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST))
  def add(@RequestBody blogPost: BlogPost): BlogPost = blogPostService.add(blogPost)

  @RequestMapping(value = Array("/{blogPostId}/addCategory"), method = Array(RequestMethod.POST))
  def addCategory(@PathVariable blogPostId: String, @RequestBody addCategoryRequest: AddCategoryRequest) =
    blogPostService.addCategoryToBlog(blogPostId, addCategoryRequest.categoryId)
}

case class AddCategoryRequest(categoryId: Array[String])

@RestController
@RequestMapping(Array("/categories"))
class CategoryRestController @Autowired()(categoryService: CategoryService) {

  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST))
  def add(@RequestBody category: Category): Category = categoryService.add(category)

  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET))
  def all(): Seq[Category] = categoryService.findAll()
}

@Controller
@RequestMapping(Array("/"))
class MainController @Autowired()(blogPostService: BlogPostService,
                                  categoryService: CategoryService) extends LazyLogging {

  @RequestMapping(Array(""))
  def mainPage() = {
    logger.info("Main page")
    MV("index", Map(
      "blogs" -> blogPostService.linksToAllBlogPosts()
    ))
  }

  @RequestMapping(Array(BLOG_POST_MAPPING))
  def mainPage(@PathVariable blogId: String) = {
    val blog = blogPostService.findById(blogId)
    MV("blog", Map(
      "blog" -> blog,
      "categories" -> categoryService.linksToCategoriesByIds(blog.categoryIds)
    ))
  }

  @RequestMapping(Array(CATEGORY_MAPPING))
  def category(@PathVariable categoryId: String) = MV("category", Map(
    "blogs" -> blogPostService.blogPostsByCategoryId(categoryId)
  ))
}

//@Aspect
//@Component
class ControllerAspect extends LazyLogging {

  @Pointcut("@within(org.springframework.stereotype.Controller)")
  def anyPublicMethod() = {
  }

  @Around("anyPublicMethod()")
  def menuConsumer(pjp: ProceedingJoinPoint): Object = {
    logger.info("Handled: {}", pjp.toString)
    pjp.proceed()
  }
}

object Controller {
  final val BLOG_POST_MAPPING = "/blog/{blogId}"
  final val CATEGORY_MAPPING = "/category/{categoryId}"

  def urlForEntity(entity: AnyRef): String = entity match {
    case s: String => s
    case b: BlogPost => urlForEntity(BLOG_POST_MAPPING.replace("{blogId}", b.id))
    case c: Category => urlForEntity(CATEGORY_MAPPING.replace("{categoryId}", c.id))
  }
}

case class MV(view: String, attributes: Map[String, _]) extends ModelAndView {
  setViewName(view)
  addAllObjects(toJava(attributes).asInstanceOf[java.util.Map[String, _]])
}