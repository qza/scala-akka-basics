package simple

import scala.annotation.tailrec

/**
  * Higher level functions using functions as arguments and results
  */
object HigherLevelFunctions extends App {

  def sumInRange(mapper: (Int) => Int, from: Int, to: Int) = { // higher level

    @tailrec
    def accumulate(acc: Int, ele: Int) : Int = {
      if (ele > to) acc else accumulate(acc + mapper(ele), ele + 1)
    }

    accumulate(0, from)
  }

  def sumNumbers(from: Int, to: Int) = sumInRange( (x) => x, from, to)

  def sumQubes(from: Int, to: Int) = sumInRange( (x) => x * x * x, from, to)

  println(sumNumbers(0, 10))

  println(sumQubes(0, 3))

}
