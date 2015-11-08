package challenge

import scala.annotation.tailrec

/**
  * Simple and tail recursive factorial
  *
  * Tail calls: when function calls other function as last action one stack frame can be used
  *
  * Tails recursive: when function call itself as the last action
  *
  */
object Factorial extends App {

  def factorial(x: Int): Int = {
    if (x == 1) x else x * factorial(x - 1) // stack overflow: not last action - after factorial x is multiplied
  }

  def factorialTailRecursive(x: Int) = {

    @tailrec
    def accumulate(acc: Int, ele: Int): Int = {
      if (ele == 0) acc else accumulate(acc * ele, ele - 1) // tail recursive
    }

    accumulate(1, x)
  }


  println(factorial(4))

  println(factorialTailRecursive(4))

}
