package com.dimafeng.scala.java

import sys.process._

object CMDExample extends App {
  println("ls" !)
  println("ls" !!)
  println("ls" #| "grep gradle " !!)
}