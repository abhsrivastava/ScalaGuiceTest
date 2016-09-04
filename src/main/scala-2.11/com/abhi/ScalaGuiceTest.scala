package com.abhi
import com.google.inject.{Inject, Provider, Provides}
import net.codingwell.scalaguice.ScalaModule
import com.google.inject.Guice

import scala.util.Random

trait Calc {
   def add(i: Int, j : Int) : Int
}

class CalcImpl1 extends Calc {
   def add(i: Int, j: Int) : Int = {
         i + j
   }
}

class CalcImpl2 extends Calc {
   def add(i: Int, j: Int) = {
      2 * (i + j)
   }
}

class CalcModule extends ScalaModule {
   def configure() = {
   }

   @Provides
   def provides() : Calc = {
      val x = Random
      val rand = Random.nextInt(10)
      println("rand: " + rand)
      rand match {
         case r if r % 2 == 0 => new CalcImpl1()
         case _ => new CalcImpl2()
      }
   }
}

class CalcLogic @Inject()(val c: Calc) {
   def add(i: Int, j: Int) : Int = c.add(i, j)
}

object ScalaGuiceTest extends App {
   val injector = Guice.createInjector(new CalcModule)
   import net.codingwell.scalaguice.InjectorExtensions._
   val calc = injector.instance[CalcLogic]
   println(calc.add(2, 5))
}
