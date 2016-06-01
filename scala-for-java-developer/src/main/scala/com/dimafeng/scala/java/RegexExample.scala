package com.dimafeng.scala.java

import scala.util.matching.Regex.Match

/**
  * Created by dimafeng on 5/30/16.
  */
object RegexExample extends App {
  val regex = s"""<a\\s+href=\"([^\"]*?)\"\\s*>([^<]*?)</a>""".r

  val text =
    s"""
       |<a href="index.html">index</a>
       |<a href="secondary.html">secondary</a>
   """.stripMargin

  val regex(href, name) = "<a href=\"test.html\">test</a>"
  println(href + ":" + name)

  for (regex(href, _) <- regex.findAllIn(text)) {
    println(href)
  }

  println(regex.replaceAllIn(text, { m => m.group(2) }))

}
