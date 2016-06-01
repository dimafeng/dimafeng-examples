package com.dimafeng.scala.java

/**
  * Created by dimafeng on 5/30/16.
  */
object StructuralTypeExample extends App {

  def print(obj: {def info: String}) = {
    println(obj.info)
  }

  class A {
    def info: String = "A"
  }

  class B {
    def info: String = "B"
  }

  print(new A)
  print(new B)
}
