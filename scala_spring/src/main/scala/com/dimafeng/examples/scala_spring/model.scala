package com.dimafeng.examples.scala_spring.model

import com.dimafeng.examples.scala_spring.model.Model._
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.{TypeAlias, Version, Id}
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field
import scala.beans.BeanInfo

@Document
class Model(@Id val id: String, @(Version@field) @(JsonIgnore@field) val version: Int)

object Model {
  final val USER_COLLECTION_NAME = "user"
  final val BLOG_COLLECTION_NAME = "blog"
  final val CATEGORY_COLLECTION_NAME = "category"
}

@TypeAlias(USER_COLLECTION_NAME)
@Document(collection = USER_COLLECTION_NAME)
//@BeanInfo
case class User(name: String,
                override val id: String = null,
                override val version: Int = 0) extends Model(id, version)

@TypeAlias(BLOG_COLLECTION_NAME)
@Document(collection = BLOG_COLLECTION_NAME)
case class BlogPost(name: String,
                    body: String,
                    categoryIds: Array[String],
                    override val id: String = null,
                    override val version: Int = 0) extends Model(id, version)

@TypeAlias(CATEGORY_COLLECTION_NAME)
@Document(collection = CATEGORY_COLLECTION_NAME)
case class Category(name: String,
                    override val id: String = null,
                    override val version: Int = 0) extends Model(id, version)