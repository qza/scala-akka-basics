package basics

/**
  * Basic model for rational numbers expressed as numerator divided by denominator
  */
object BasicModel extends App {

  val a = new Rational(1, 2)
  val b = new Rational(2, 3)

  println(a + " + " +  b + " = " + a.add(b))
  println(a + " - " +  b + " = " + a.sub(b))
  println(a + " * " +  b + " = " + a.mul(b))
  println(a + " / " +  b + " = " + a.div(b))

  class Rational(x: Int, y: Int) {

    def numer = x

    def denum = y

    def neg = new Rational(-numer, denum)

    def add(other: Rational) = new Rational(numer * other.denum + other.numer * denum, denum * other.denum)

    def sub(other: Rational) = add(other.neg)

    def mul(other: Rational) = new Rational(numer * other.numer, denum * other.denum)

    def div(other: Rational) = new Rational(numer * other.denum, denum * other.numer)

    override def toString = numer + "/" + denum
  }

}




