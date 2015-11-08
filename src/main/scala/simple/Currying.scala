package simple

import scala.annotation.tailrec

/**
  * Currying style
  *
  * Currying is the technique of translating the evaluation of a function that takes multiple arguments
  * (or a tuple of arguments) into evaluating a sequence of functions, each with a single argument
  */
object Currying extends App {

  // mappers

  def identity(x: Int) = x

  def square(x: Int) = x * x

  // combiners

  def sum(x: Int, y: Int) = x + y

  def product(x: Int, y: Int) = x * y

  /*
   * Breaking down multiple arguments function into a series of functions that take part of the arguments
   */
  def inRange(mapper: (Int) => Int, combiner: (Int, Int) => Int, zero: Int)(from: Int, to: Int): Int = {

    @tailrec
    def accumulate(acc: Int, ele: Int): Int = {
      if (ele > to) acc else accumulate(combiner(acc, mapper(ele)), ele + 1)
    }

    accumulate(zero, from)
  }

  // usage

  def identitySumInRange(from: Int, to: Int) = inRange(identity, sum, 0)(from, to)

  def squareProductInRange(from: Int, to: Int) = inRange(square, product, 1)(from, to)

  println(identitySumInRange(0, 10))

  println(squareProductInRange(1, 3))

}

