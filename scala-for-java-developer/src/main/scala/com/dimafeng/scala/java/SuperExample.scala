package com.dimafeng.scala.java


object SuperExample extends App{
  (new A with B).printToString()
}

class A {
  override def toString = s"A()"
}

trait B {
  def printToString() = println(super.toString)
}