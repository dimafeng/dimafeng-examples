package com.dimafeng.scala.java

import scala.util.Random

/**
  * Created by dimafeng on 5/30/16.
  */
object TraitExample extends App {

  new Car {
    override val color: String = "red"
  }.print()

}

trait Car {
  val color: String

  def print() = {
    println(s"Car is ${color}")
  }
}

class State {
  private var value = 0

  def change(): Unit = {
    value = Random.nextInt()
  }

  def current = value
}