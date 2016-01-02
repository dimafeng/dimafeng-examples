package com.dimafeng.examples.scala_spring.dao

import com.dimafeng.examples.scala_spring.dao.Mongo._
import com.dimafeng.examples.scala_spring.model.{BlogPost, Category, User}
import com.mongodb.DBObject
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.{BasicQuery, BasicUpdate}
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

trait UserRepository extends PagingAndSortingRepository[User, String]

trait BlogPostRepository extends PagingAndSortingRepository[BlogPost, String] with BlogPostRepositoryCustom {

  @Query("{'categoryIds': ?0 }")
  def findByCategoryId(categoryId: String): Array[BlogPost]
}

trait BlogPostRepositoryCustom {

  def addCategoryToBlogPostId(blogPostId: String, categoryId: Array[String])
}

class BlogPostRepositoryImpl @Autowired()(mongoTemplate: MongoTemplate) extends BlogPostRepositoryCustom {

  override def addCategoryToBlogPostId(blogPostId: String, categoryId: Array[String]): Unit =
    mongoTemplate.findAndModify(
      MO("_id" -> new ObjectId(blogPostId)).toQuery,
      $addToSet("categoryIds" -> categoryId).toUpdateWithLock,
      classOf[BlogPost])

}

trait CategoryRepository extends PagingAndSortingRepository[Category, String] {

  @Query("{'_id': {$in: ?0 }}")
  def findByIds(ids: Array[String]): Array[Category]
}

object Mongo {
  val MO = MongoDBObject

  implicit def dbObjectToQueryGenerator[T <: DBObject](value: T): QueryGenerator = new QueryGenerator(value)

  class QueryGenerator(val dbObject: DBObject) {
    def toQuery: BasicQuery = {
      new BasicQuery(dbObject)
    }

    def toUpdateWithLock: BasicUpdate = {
      new BasicUpdate($inc("version" -> 1) ++ dbObject)
    }

    def toUpdate: BasicUpdate = {
      new BasicUpdate(dbObject)
    }
  }
}