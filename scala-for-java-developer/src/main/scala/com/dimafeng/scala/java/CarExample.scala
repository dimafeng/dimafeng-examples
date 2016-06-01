package com.dimafeng.scala.java

package cat {

  abstract class Car {
    def specs() = {
      print(s"Car with ${wheels} wheels")
    }

    def wheels: Int
  }

  trait FourWheel {
    def wheels = 4
  }

  object CarExample extends App {
    (new Car with FourWheel).specs()
  }

}