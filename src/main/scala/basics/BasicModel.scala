package basics

import Math.abs

/**
  * Basic model with example Rational class
  */
object BasicModel extends App {

  val a = new Rational(1, 2)
  val b = new Rational(2, 3)

  val one = new Rational(1)

  println(a + " + " + b + " = " + a.add(b))
  println(a + " - " + b + " = " + a.sub(b))
  println(a + " * " + b + " = " + a.mul(b))
  println(a + " / " + b + " = " + a.div(b))

  println(one + " + " + one + " = " + one.add(one))
  println(one + " - " + b + " = " + one.sub(b))
  println(one + " * " + a + " = " + one.mul(a))
  println(one + " / " + a + " = " + one.div(a))

  println(one + " max " + one + " = " + one.max(one))
  println(a + " max " + b + " = " + a.max(b))

  /*
   * Rational class expressed as numerator divided by denominator
   */
  class Rational(x: Int, y: Int) {
    // primary constructor

    require(y > 0, "denominator must not be equal to zero")

    def numer = x

    def denom = y

    def this(x: Int) = this(x, 1) // custom constructor

    def neg = new Rational(-numer, denom)

    def add(other: Rational) = new Rational(numer * other.denom + other.numer * denom, denom * other.denom)

    def sub(other: Rational) = add(other.neg)

    def mul(other: Rational) = new Rational(numer * other.numer, denom * other.denom)

    def div(other: Rational) = new Rational(numer * other.denom, denom * other.numer)

    def less(other: Rational) = numer * other.denom < other.numer * denom

    def max(other: Rational) = if (other.less(this)) this else other

    override def toString = {
      val g = abs(gcd(numer, denom)) // precomputed value
      (numer / g) + "/" + (denom / g)
    }

    /*
     * Private methods
     */

    private def gcd(x: Int, y: Int): Int = if (y == 0) x else gcd(y, x % y) // greatest common divider

  }

}




